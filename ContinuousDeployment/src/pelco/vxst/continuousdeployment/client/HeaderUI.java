package pelco.vxst.continuousdeployment.client;
import pelco.vxst.continuousdeployment.client.Role;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderUI extends Composite {

	private static HeaderUIUiBinder uiBinder = GWT
			.create(HeaderUIUiBinder.class);

	interface HeaderUIUiBinder extends UiBinder<Widget, HeaderUI> {
	}

	public HeaderUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	final Role roles = new Role();
	final User users = new User(); 
	
	@UiHandler("addRole")
	void handleAddRole(ClickEvent event) {
		RootPanel.get("content").clear();
		//roles.loadRole();
		RootPanel.get("content").add(roles);
	}
	
	@UiHandler("addUser")
	void handleAddUser(ClickEvent event) {
		RootPanel.get("content").clear();
		RootPanel.get("content").add(users);
	}
}
