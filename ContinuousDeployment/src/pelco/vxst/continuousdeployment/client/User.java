package pelco.vxst.continuousdeployment.client;

import java.util.ArrayList;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.NavForm;
import com.github.gwtbootstrap.client.ui.Row;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class User extends Composite {

	private static UserUiBinder uiBinder = GWT.create(UserUiBinder.class);
	
	interface UserUiBinder extends UiBinder<Widget, User> {
	}
	
	@UiField
	NavForm priority;
	@UiField
	NavForm userName;
	@UiField
	Button addUserButton;
	@UiField
	FlexTable userTable;
	@UiField
	Row dupError;
	
	private ArrayList<String> userList = new ArrayList<String>();
	private final EnduraSoapServiceAsync enduraSoapService = GWT
			.create(EnduraSoapService.class);
	
	public User() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		//Role Table Headers
		userTable.setText(0, 0, "User");
		userTable.setText(0, 1, "Roles");
		userTable.setText(0, 2, "Remove");
		userTable.setCellPadding(2);
		userTable.setBorderWidth(1);
		
		addUserButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( !checkDup(userName.getTextBox().getText()) )
					addRowToTable(Integer.parseInt(priority.getTextBox().getText()), userName.getTextBox().getText());
				else
				{
					dupError.add(new Alert("Entered Duplicate User", AlertType.ERROR, true));
				}
			}
		});
	}
	
	private Boolean checkDup (final String user) {
		if(userList.contains(user))
			return true;
		return false;
	}
	
	private void addRowToTable(final Integer priority, final String user) {
		final int currentRow = userTable.getRowCount();
		Button removeRoleButton = new Button("x");
		
		userTable.setText(currentRow, 0, Integer.toString(priority));
		userTable.setText(currentRow, 1, user);
		userTable.setWidget(currentRow, 2, removeRoleButton);
		userList.add(user);
		
		removeRoleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int removeIndex = userList.indexOf(user);
				
				userTable.removeRow(removeIndex + 1);
				userList.remove(removeIndex);
			}
		});
	}
	
	public void loadUsers() {
		
	enduraSoapService.getRoles(new AsyncCallback<String>() {
	
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void onSuccess(final String result) {
			// TODO Auto-generated method stub
			
			
		}});
				
	}
}
