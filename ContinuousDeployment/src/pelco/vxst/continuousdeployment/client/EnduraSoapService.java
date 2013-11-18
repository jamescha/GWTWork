package pelco.vxst.continuousdeployment.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("enduraSoap")
public interface EnduraSoapService extends RemoteService {
	String enduraSoapServer(String name) throws IllegalArgumentException;
	String getRoles() throws IllegalArgumentException;
}