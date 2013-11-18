package pelco.vxst.continuousdeployment.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>EnduraSoapService</code>.
 */
public interface EnduraSoapServiceAsync {
	void enduraSoapServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void getRoles(AsyncCallback<String> asyncCallback);
}
