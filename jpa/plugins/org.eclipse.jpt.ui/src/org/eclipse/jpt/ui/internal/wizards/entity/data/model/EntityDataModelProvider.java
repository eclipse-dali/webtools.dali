/***********************************************************************
 * Copyright (c) 2008, 2009 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation
 ***********************************************************************/
package org.eclipse.jpt.ui.internal.wizards.entity.data.model;

import com.ibm.icu.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.wizards.entity.EntityWizardMsg;
import org.eclipse.jpt.ui.internal.wizards.entity.data.operation.NewEntityClassOperation;
import org.eclipse.jst.j2ee.internal.common.J2EECommonMessages;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.operations.NewJavaClassDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;

public class EntityDataModelProvider extends NewJavaClassDataModelProvider implements IEntityDataModelProperties{

	@Override
	public IDataModelOperation getDefaultOperation() {
		return new NewEntityClassOperation(getDataModel());
	}
	
	/**
	 * Extends: <code>IDataModelProvider#getPropertyNames()</code>
	 * and add own data model's properties specific for the entity model
	 * 
	 * @see org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider#getPropertyNames()
	 */
	@Override
	public Set getPropertyNames() {		
		Set propertyNames = super.getPropertyNames();
		propertyNames.add(INHERITANCE);
		propertyNames.add(ENTITY);
		propertyNames.add(MAPPED_AS_SUPERCLASS);
		propertyNames.add(INHERITANCE_STRATEGY);
		propertyNames.add(XML_SUPPORT);
		propertyNames.add(XML_NAME);
		propertyNames.add(ENTITY_NAME);
		propertyNames.add(TABLE_NAME_DEFAULT);		
		propertyNames.add(TABLE_NAME);
		propertyNames.add(ENTITY_FIELDS);
		propertyNames.add(PK_FIELDS);
		propertyNames.add(FIELD_ACCESS_TYPE);
		propertyNames.add(PROPERTY_ACCESS_TYPE);
		return propertyNames;
	}
	
	/**
	 * Returns the default value of the parameter (which should present a valid data model property).  
	 * This method does not accept a null parameter. It may return null. 
	 * 
	 * @see NewJavaClassDataModelProvider#getDefaultProperty(String)
	 * @see IDataModelProvider#getDefaultProperty(String)
	 * 
	 * @param propertyName
	 * @return Object default value of property
	 */
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(INHERITANCE)) {
			return Boolean.FALSE;
		} else if (propertyName.equals(ENTITY)) {
			return Boolean.TRUE;
		} else if (propertyName.equals(MAPPED_AS_SUPERCLASS)) {
			return Boolean.FALSE;
		} else if (propertyName.equals(XML_SUPPORT)) {
			return Boolean.FALSE;
		} else if (propertyName.equals(XML_NAME)) {
			return EMPTY_STRING;			
		} else if (propertyName.equals(ENTITY_NAME)) {
			return getStringProperty(CLASS_NAME);			
		} else if (propertyName.equals(TABLE_NAME_DEFAULT)) {
			return Boolean.TRUE;
		} else if (propertyName.equals(TABLE_NAME)) {
			return getStringProperty(CLASS_NAME);			
		} else if (propertyName.equals(INHERITANCE_STRATEGY)) {
			return EMPTY_STRING; 
		} else if (propertyName.equals(SUPERCLASS)) {
			return EMPTY_STRING;
		} else if (propertyName.equals(ENTITY_FIELDS)) {
			return new ArrayList<EntityRow>();
		} else if (propertyName.equals(PK_FIELDS)) {
			return new ArrayList<String>();
		} else if (propertyName.equals(FIELD_ACCESS_TYPE)) {
			return Boolean.TRUE;
		} else if (propertyName.equals(PROPERTY_ACCESS_TYPE)) {
			return Boolean.FALSE;			
		} 
		// Otherwise check super for default value for property
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (propertyName.equals(PROJECT_NAME) || propertyName.equals(XML_SUPPORT)) {
			this.model.notifyPropertyChange(XML_NAME, IDataModel.VALID_VALUES_CHG);
		}
		return ok;
	}

	/* Adds additional check to the model validation
	 * @see org.eclipse.jst.j2ee.internal.common.operations.NewJavaClassDataModelProvider#validate(java.lang.String)
	 */
	@Override
	public IStatus validate(String propertyName) {
		IStatus result = super.validate(propertyName);
		if (propertyName.equals(JAVA_PACKAGE)) {
			return validateJavaPackage(getStringProperty(propertyName));
		}
		if (propertyName.equals(SUPERCLASS) && EMPTY_STRING.equals(getStringProperty(propertyName))) {
			return WTPCommonPlugin.OK_STATUS;
		}
		if (propertyName.equals(XML_NAME)) {
			return validateXmlName(getStringProperty(propertyName));
		}
		if (propertyName.equals(ENTITY_FIELDS)) {
			return validateFieldsList((ArrayList<EntityRow>) getProperty(propertyName));
		}
		return result;		
	}
	
	/**
	 * This method is intended for internal use only. It will be used to validate the correctness of entity package
	 * in accordance with Java convention requirements. This method will accept a null parameter. 
	 * 
	 * @see NewFilterClassDataModelProvider#validate(String)
	 * 
	 * @param packName
	 * @return IStatus is the package name satisfies Java convention requirements
	 */
	
	private IStatus validateJavaPackage(String packName) {		
		if (packName == null || packName.equals(EMPTY_STRING)) {
			return WTPCommonPlugin.createWarningStatus(EntityWizardMsg.DEFAULT_PACKAGE_WARNING);
		}			
		// Use standard java conventions to validate the package name
		IStatus javaStatus = JavaConventions.validatePackageName(packName, JavaCore.VERSION_1_5, JavaCore.VERSION_1_5);
		if (javaStatus.getSeverity() == IStatus.ERROR) {
			String msg = J2EECommonMessages.ERR_JAVA_PACAKGE_NAME_INVALID + javaStatus.getMessage();				
			return WTPCommonPlugin.createErrorStatus(msg);
		} else if (javaStatus.getSeverity() == IStatus.WARNING) {
			String msg = J2EECommonMessages.ERR_JAVA_PACKAGE_NAME_WARNING + javaStatus.getMessage();
			return WTPCommonPlugin.createWarningStatus(msg);
		}		
		// java package name is valid
		return WTPCommonPlugin.OK_STATUS;
	}
	
	/**
	 * This method is intended for internal use only.  It will be used to validate 
	 * the correctness of xml file location. 
	 * This method will accept a null parameter. 
	 * 
	 * @see NewFilterClassDataModelProvider#validate(String)
	 * 
	 * @param xmlName
	 * @return IStatus is the package name satisfies Java convention requirements
	 */
	private IStatus validateXmlName(String xmlName) {
		if (getBooleanProperty(XML_SUPPORT)) {
			String projectName = this.model.getStringProperty(PROJECT_NAME);
			IProject project = ProjectUtilities.getProject(projectName);
			if (project != null) {
				//TODO need to check content type as well since user can type in a file name, should have a different error message for invalid content type
				JpaFile jpaFile = JptCorePlugin.getJpaFile(project, xmlName);
				if (jpaFile == null) {
					return new Status(
						IStatus.ERROR, JptUiPlugin.PLUGIN_ID,
							EntityWizardMsg.INVALID_XML_NAME);
				}
			}
		}
		return Status.OK_STATUS;
	}
	
	
	/**
	 * This method is intended for internal use only. It will be used to validate the entity fields
	 * list to ensure there are not any duplicates. This method will accept a null parameter. 
	 * 
	 * @see NewFilterClassDataModelProvider#validate(String)
	 * 
	 * @param entities
	 * @return IStatus is the fields names are unique
	 */
	private IStatus validateFieldsList(ArrayList<EntityRow> entities) {
		if (entities != null && !entities.isEmpty()) {
			// Ensure there are not duplicate entries in the list
			boolean dup = hasDuplicatesInEntityFields(entities);
			if (dup) {
				String msg = EntityWizardMsg.DUPLICATED_ENTITY_NAMES_MESSAGE;				
				return WTPCommonPlugin.createErrorStatus(msg);
			}
			// Ensure that the entries in the list are valid
			String errorMsg = checkInputElementsTypeValidation(entities);
			if (errorMsg != null) {
				return WTPCommonPlugin.createErrorStatus(errorMsg);
			}
			String warningMsg = checkInputElementsTypeExistence(entities);
			if (warningMsg != null) {
				return WTPCommonPlugin.createWarningStatus(warningMsg);
			}
		}
		return WTPCommonPlugin.OK_STATUS;
	}
	
	private String checkInputElementsTypeValidation(List<EntityRow> inputElements) {
		IStatus validateFieldTypeStatus = Status.OK_STATUS;
		for (EntityRow entityRow: inputElements) {
			if (entityRow.isKey() && !entityRow.couldBeKey()) {
				String message = MessageFormat.format(
						EntityWizardMsg.EntityDataModelProvider_invalidPKType, new Object[]{entityRow.getFqnTypeName()});
				validateFieldTypeStatus = new Status(IStatus.ERROR,
						JptUiPlugin.PLUGIN_ID, message);
				break;				
			}			
			String sig = null;
			try {
				sig = Signature.createTypeSignature(entityRow.getFqnTypeName(), true);
			} catch (IllegalArgumentException e) {
				String message = MessageFormat.format(EntityWizardMsg.EntityDataModelProvider_invalidArgument, new Object[]{e.getLocalizedMessage()});
				validateFieldTypeStatus = new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, message);
				break;
			}
			if (sig == null){
				validateFieldTypeStatus = JavaConventions.validateJavaTypeName(entityRow.getType(), JavaCore.VERSION_1_5, JavaCore.VERSION_1_5);
				break;
			}
			int sigType = Signature.getTypeSignatureKind(sig);
			if (sigType == Signature.BASE_TYPE_SIGNATURE) {
				continue;
			}
			else if (sigType == Signature.ARRAY_TYPE_SIGNATURE) {
				String elementSignature = Signature.getElementType(sig);
				if (Signature.getTypeSignatureKind(elementSignature) == Signature.BASE_TYPE_SIGNATURE) {
					continue;
				}
			}
		}
		if (!validateFieldTypeStatus.isOK()) {
			return validateFieldTypeStatus.getMessage();
		}
		return null;
	}
	
	private String checkInputElementsTypeExistence(List<EntityRow> inputElements) {
		IStatus validateFieldTypeStatus=Status.OK_STATUS;
		for (EntityRow entityRow: inputElements) {
			String sig = Signature.createTypeSignature(entityRow.getFqnTypeName() ,true);
			if (sig == null) {
				String message = MessageFormat.format(
						EntityWizardMsg.EntityDataModelProvider_typeNotInProjectClasspath, new Object[]{entityRow.getFqnTypeName()});
				validateFieldTypeStatus = new Status(IStatus.ERROR,
						JptUiPlugin.PLUGIN_ID, message);
				break;
			}
			int sigType = Signature.getTypeSignatureKind(sig);
			if (sigType == Signature.BASE_TYPE_SIGNATURE){
				continue;
			}
			else if (sigType == Signature.ARRAY_TYPE_SIGNATURE) {
				String elementSignature = Signature.getElementType(sig);
				if(Signature.getTypeSignatureKind(elementSignature) == Signature.BASE_TYPE_SIGNATURE){
					continue;
				}
				String qualifiedName = Signature.toString(elementSignature);
				IProject project = (IProject) getProperty(INewJavaClassDataModelProperties.PROJECT);
				IJavaProject javaProject = JavaCore.create(project);
				IType type = null;
				try {
					type = javaProject.findType(qualifiedName);
				} catch (JavaModelException e) {
					validateFieldTypeStatus = e.getStatus();
					break;
				}
				if (type == null) {
					String message = MessageFormat.format(
							EntityWizardMsg.EntityDataModelProvider_typeNotInProjectClasspath, new Object[]{entityRow.getFqnTypeName()});
					validateFieldTypeStatus = new Status(IStatus.ERROR,
							JptUiPlugin.PLUGIN_ID, message);
					break;
				}
			}
			else {
				IProject project = (IProject) getProperty(INewJavaClassDataModelProperties.PROJECT);
				IJavaProject javaProject = JavaCore.create(project);
				IType type = null;
				try {
					type = javaProject.findType(entityRow.getFqnTypeName());
				} catch (JavaModelException e) {
					validateFieldTypeStatus = e.getStatus();
					break;
				}
				if (type == null) {
					String message = MessageFormat.format(
							EntityWizardMsg.EntityDataModelProvider_typeNotInProjectClasspath, new Object[]{entityRow.getFqnTypeName()});
					validateFieldTypeStatus = new Status(IStatus.ERROR,
							JptUiPlugin.PLUGIN_ID, message);
					break;
				}
			}
		}
		if(!validateFieldTypeStatus.isOK()) {
			return validateFieldTypeStatus.getMessage();
		}
		return null;
	}
			
			
	
	/**
	 * This method is intended for internal use only. It provides a simple algorithm for detecting
	 * if there are duplicate entries in a list. It will accept a null parameter. It will return
	 * boolean.
	 * 
	 * @param input
	 * @return boolean are there duplications in the list
	 */
	private boolean hasDuplicatesInEntityFields(ArrayList<EntityRow> input) {
		if (input == null) {
			return false;
		}
		int n = input.size();
		// nested for loops to check each element to see if other elements are the same
		for (int i = 0; i < n; i++) {
			EntityRow entity = input.get(i);
			for (int j = i + 1; j < n; j++) {
				EntityRow intEntity = input.get(j);
				if (intEntity.getName().equals(entity.getName())) {
					return true;
				}
			}
		}		
		return false;
	}		
}
