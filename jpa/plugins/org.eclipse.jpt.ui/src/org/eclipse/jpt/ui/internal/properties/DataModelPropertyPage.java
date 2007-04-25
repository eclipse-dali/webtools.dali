package org.eclipse.jpt.ui.internal.properties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.wst.common.frameworks.datamodel.DataModelEvent;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelListener;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelSynchHelper;
import org.eclipse.wst.common.frameworks.internal.ui.ValidationStatus;

public abstract class DataModelPropertyPage 
	extends PropertyPage
	implements Listener, IDataModelListener
{
	protected IDataModel model;
	
	private ValidationStatus status = new ValidationStatus();
	private Map validationMap;
	private String[] validationPropertyNames;
	private boolean isValidating = false;
	
	protected DataModelSynchHelper synchHelper;
	
	private String infopopID;
	
	
	protected DataModelPropertyPage(IDataModel model) {
		super();
		this.model = model;
		model.addListener(this);
		synchHelper = initializeSynchHelper(model);
	}
	

	/**
	 * @return
	 */
	public DataModelSynchHelper initializeSynchHelper(IDataModel dm) {
		return new DataModelSynchHelper(dm);
	}

	
	@Override
	protected Control createContents(Composite parent) {
		Composite top = createTopLevelComposite(parent);
		setupInfopop(top);
		setDefaults();
		addListeners();
		initializeValidationProperties();
		return top;
	}
	
	private void initializeValidationProperties() {
		validationPropertyNames = getValidationPropertyNames();
		if (validationPropertyNames == null || validationPropertyNames.length == 0)
			validationMap = Collections.EMPTY_MAP;
		else {
			validationMap = new HashMap(validationPropertyNames.length);
			for (int i = 0; i < validationPropertyNames.length; i++)
				validationMap.put(validationPropertyNames[i], new Integer(i));
		}
	}

	/**
	 * Subclass should return the model property names that need to be validated on this page in the
	 * order that they should present their messages.
	 * 
	 * @return
	 */
	protected abstract String[] getValidationPropertyNames();

	/**
	 * Return the top level Composite for this page.
	 */
	protected abstract Composite createTopLevelComposite(Composite parent);

	/**
	 * Set up info pop hooks if set.
	 */
	protected void setupInfopop(Control parent) {
		if (getInfopopID() != null)
			PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, getInfopopID());
	}

	/**
	 * Setup the default values for this page. Subclasses should override to provide appropriate
	 * defaults.
	 */
	protected void setDefaults() {
		restoreDefaultSettings();
	}

	/**
	 * Subclasses should implement this method if they have default settings that have been stored
	 * and need to be restored.
	 * 
	 * @see storeDefaultSettings()
	 */
	protected void restoreDefaultSettings() {
	}

	/**
	 * Add Listeners to controls at this point to avoid unnecessary events. Subclasses should
	 * override to add listeners to its controls.
	 */
	protected void addListeners() {
	}

	/**
	 * Exiting the page. Subclasses may extend.
	 */
	protected void exit() {
	}

	protected boolean getStatus(Integer key) {
		return status.hasError(key);
	}

	/**
	 * Sent when an event that the receiver has registered for occurs. If a subclass overrides this
	 * method, it must call super.
	 * 
	 * @param event
	 *            the event which occurred
	 */
	public void handleEvent(org.eclipse.swt.widgets.Event event) {
	}

	/**
	 * Set the error message for this page based on the last error in the ValidationStatus.
	 */
	protected void setErrorMessage() {
		String error = status.getLastErrMsg();
		if (error == null) {
			if (getErrorMessage() != null)
				setErrorMessage((String) null);
			String warning = status.getLastWarningMsg();
			if (warning == null) {
				if (getMessage() != null && getMessageType() == IMessageProvider.WARNING)
					setMessage(null, IMessageProvider.WARNING);
				else {
					String info = status.getLastInfoMsg();
					if (info == null) {
						if (getMessage() != null && getMessageType() == IMessageProvider.INFORMATION)
							setMessage(null, IMessageProvider.INFORMATION);
					} else if (!info.equals(getMessage())) {
						setMessage(info, IMessageProvider.INFORMATION);
					}
				}
			} else if (!warning.equals(getMessage()))
				setMessage(warning, IMessageProvider.WARNING);
		} else if (!error.equals(getErrorMessage()))
							setErrorMessage(error);
						}

	protected void setErrorStatus(Integer key, String errorMessage) {
		status.setErrorStatus(key, errorMessage);
	}

	protected void setWarningStatus(Integer key, String warningMessage) {
		status.setWarningStatus(key, warningMessage);
	}
	
	protected void setInfoStatus(Integer key, String infoMessage) {
		status.setInfoStatus(key, infoMessage);
	}

	protected void setOKStatus(Integer key) {
		status.setOKStatus(key);
	}

	/**
	 * This should be called by the Wizard just prior to running the performFinish operation.
	 * Subclasses should override to store their default settings.
	 */
	public void storeDefaultSettings() {
	}

	/**
	 * The page is now being validated. At this time, each control is validated and then the
	 * controls are updated based on the results in the ValidationStatus which was updated during
	 * <code>validateControls()</code>. Finally, it will display the last error message and it
	 * will set the page complete. Subclasses will not typically override this method.
	 */
	protected void validatePage() {
		if (!isValidating) {
			isValidating = true;
			try {
				validateControlsBase();
				updateControls();
				setErrorMessage();
				setValid(status.getLastErrMsg() == null);
			} 
			finally {
				isValidating = false;
			}
		}
	}

	/**
	 * Validate individual controls. Use validation keys to keep track of errors.
	 * 
	 * @see setOKStatus(Integer) and setErrorMessage(Integer, String)
	 */
	protected final String validateControlsBase() {
		if (!validationMap.isEmpty()) {
			String propName;
			for (int i = 0; i < validationPropertyNames.length; i++) {
				propName = validationPropertyNames[i];
				Integer valKey = (Integer) validationMap.get(propName);
				if (valKey != null)
					validateProperty(propName, valKey);
				if (!getStatus(valKey))
					return propName;
			}
		}
		return null;
	}

	/**
	 * @param propertyName
	 * @param validationkey
	 */
	private void validateProperty(String propertyName, Integer validationKey) {
		setOKStatus(validationKey);
		IStatus status1 = model.validateProperty(propertyName);
		if (!status1.isOK()) {
			String message = status1.isMultiStatus() ? status1.getChildren()[0].getMessage() : status1.getMessage();
			switch (status1.getSeverity()) {
				case IStatus.ERROR :
					setErrorStatus(validationKey, message);
					break;
				case IStatus.WARNING :
					setWarningStatus(validationKey, message);
					break;
				case IStatus.INFO :
					setInfoStatus(validationKey, message);
					break;
			}
		}
	}

	/**
	 * Update the enablement of controls after validation. Sublcasses should check the status of
	 * validation keys to determine enablement.
	 */
	protected void updateControls() {
	}
	
	
	/*
	 * If a property changes that we want to validate, force validation on this page.
	 * 
	 * @see org.eclipse.wst.common.frameworks.internal.operation.WTPOperationDataModelListener#propertyChanged(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void propertyChanged(DataModelEvent event) {
		String propertyName = event.getPropertyName();
		if (validationPropertyNames != null && (event.getFlag() == DataModelEvent.VALUE_CHG || (!isValid() && event.getFlag() == DataModelEvent.VALID_VALUES_CHG))) {
			for (int i = 0; i < validationPropertyNames.length; i++) {
				if (validationPropertyNames[i].equals(propertyName)) {
					validatePage();
					break;
				}
			}
		}
	}

	/**
	 * @return Returns the model.
	 */
	protected IDataModel getDataModel() {
		return model;
	}

	public void dispose() {
		super.dispose();
		if (synchHelper != null) {
			synchHelper.dispose();
			synchHelper = null;
		}
	}

	protected String getInfopopID() {
		return infopopID;
	}

	public void setInfopopID(String infopopID) {
		this.infopopID = infopopID;
	}
}
