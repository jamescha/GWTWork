package pelco.vxst.continuousdeployment.server;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;

import pelco.vxst.continuousdeployment.client.EnduraSoapService;
import pelco.vxst.continuousdeployment.shared.FieldVerifier;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */

@SuppressWarnings("serial")
public class EnduraSoapServiceImpl extends RemoteServiceServlet implements
		EnduraSoapService {
	ScriptingContainer container;
	
	@SuppressWarnings("deprecation")
	public String enduraSoapServer(String input) throws IllegalArgumentException {
		String classpath = getServletContext().getRealPath("/WEB-INF/classes");
        List<String> loadPaths = Arrays.asList(classpath.split(File.pathSeparator));
        container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
        container.getProvider().setLoadPaths(loadPaths);
		container.runScriptlet( "require 'rubygems'\n"
							  + "require 'endura_soap/user_and_role'\n"
							  + "userrole = EnduraSOAP::UserAndRole.new('192.168.5.10', 60001)\n"
							  + "roles = userrole.role_get_all.to_hash\n"
							  + "item = roles[:role_get_all_response][:page][:item]\n"
							  + "name = item[:name]\n"
							  + "priority = item[:priority]");
		String name = (String) container.get("name");
		String priority = (String) container.get("priority");			
		
		
		// Verify that the input is valid. 
				if (!FieldVerifier.isValidName(input)) {
					// If the input is not valid, throw an IllegalArgumentException back to
					// the client.
					throw new IllegalArgumentException(
							"Name must be at least 4 characters long");
				}

				String serverInfo = getServletContext().getServerInfo();
				String userAgent = getThreadLocalRequest().getHeader("User-Agent");

				// Escape data from the client to avoid cross-site script vulnerabilities.
				input = escapeHtml(input);
				userAgent = escapeHtml(userAgent);

				return "Hello, " + input + "!<br><br>I am running " + serverInfo
						+ ".<br><br>It looks like you are using:<br>" + userAgent;
		
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String getRoles() throws IllegalArgumentException {
		String classpath = getServletContext().getRealPath("/WEB-INF/classes");
        List<String> loadPaths = Arrays.asList(classpath.split(File.pathSeparator));
        HashMap<String, Integer> roleList = new HashMap<>();
        
        container = new ScriptingContainer(LocalContextScope.SINGLETHREAD,LocalVariableBehavior.PERSISTENT);
        container.getProvider().setLoadPaths(loadPaths);
		container.runScriptlet( "require 'rubygems'\n"
							  + "require 'endura_soap/user_and_role'\n"
							  + "userrole = EnduraSOAP::UserAndRole.new('192.168.5.10', 60001)\n"
							  + "roles = userrole.role_get_all.to_hash\n"
							  + "puts item = roles[:role_get_all_response][:page][:item]\n"
							  + "puts name = item[:name].to_s\n"
							  + "puts priority = item[:priority]");
		//Object test =  container.get("name");
		Object test =  container.get("roles");
		
		return test.toString();
	}
}
