/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.operation.NewDynamicEntityClassOperation;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.J2EECommonMessages;
import org.eclipse.jst.j2ee.internal.common.operations.NewJavaClassDataModelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;

import com.ibm.icu.text.MessageFormat;

public class DynamicEntityDataModelProvider extends NewJavaClassDataModelProvider implements IEntityDataModelProperties {

	private static final String DOT = "."; //$NON-NLS-1$

	@Override
	public IDataModelOperation getDefaultOperation() {
		return new NewDynamicEntityClassOperation(getDataModel());
	}

	// ************** model properties ****************
	/**
	 * Extends: <code>IDataModelProvider#getPropertyNames()</code>
	 * and add own data model's properties specific for the dynamic entity model
	 * 
	 * @see org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider#getPropertyNames()
	 */
	@Override
	public Set<String> getPropertyNames() {		
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(ENTITY);
		propertyNames.add(XML_NAME);
		propertyNames.add(ENTITY_NAME);
		propertyNames.add(TABLE_NAME_DEFAULT);		
		propertyNames.add(TABLE_NAME);
		propertyNames.add(ENTITY_FIELDS);
		propertyNames.add(PK_FIELDS);
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

		if (propertyName.equals(ENTITY)) {
			return Boolean.TRUE;
		} else if (propertyName.equals(XML_NAME)) {
			return EMPTY_STRING;			
		} else if (propertyName.equals(ENTITY_NAME)) {
			return getStringProperty(CLASS_NAME);			
		} else if (propertyName.equals(TABLE_NAME_DEFAULT)) {
			return Boolean.TRUE;
		} else if (propertyName.equals(TABLE_NAME)) {
			return getStringProperty(CLASS_NAME);			
		} else if (propertyName.equals(ENTITY_FIELDS)) {
			return new ArrayList<DynamicEntityField>();
		} else if (propertyName.equals(PK_FIELDS)) {
			return new ArrayList<DynamicEntityField>();
		} else if (propertyName.equals(JAVA_PACKAGE_FRAGMENT_ROOT))
			return getJavaPackageFragmentRoots();

		// Otherwise check super for default value for property
		return super.getDefaultProperty(propertyName);
	}

	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (ok) {
			if (PROJECT_NAME.equals(propertyName) || XML_SUPPORT.equals(propertyName)) {
				this.model.notifyPropertyChange(XML_NAME, IDataModel.VALID_VALUES_CHG);
			}
		}
		return ok;
	}

	/**
	 * Returns all the the existing source path roots cross the selected project
	 * @return a set of IPackageFragmentRoot with the kind of source path
	 */
	protected IPackageFragmentRoot[] getJavaPackageFragmentRoots() {
		JpaProject jpaProject = getJpaProject();
		IPackageFragmentRoot[] packRoots = new IPackageFragmentRoot[0];
		List<IPackageFragmentRoot> rootList = new ArrayList<IPackageFragmentRoot>();
		if (jpaProject != null) {
			IJavaProject javaProject = jpaProject.getJavaProject();
			if (javaProject != null) {
				try {
					packRoots = javaProject.getAllPackageFragmentRoots();
					for (IPackageFragmentRoot root : packRoots) {
						if (root.getKind() == IPackageFragmentRoot.K_SOURCE) {
							rootList.add(root);
						}
					}
					packRoots = new IPackageFragmentRoot[rootList.size()];
					rootList.toArray(packRoots);
				} catch (JavaModelException e) {
					// fall through
					JptJpaEclipseLinkUiPlugin.log(e);
				}
			}
		}
		return packRoots;
	}

	//************** model validation ****************

	@Override
	public IStatus validate(String propertyName) {
		IStatus result = super.validate(propertyName);
		if (propertyName.equals(JAVA_PACKAGE)) {
			return validateJavaPackage(getStringProperty(propertyName));
		}
		if (propertyName.equals(XML_NAME)) {
			return validateXmlName(getStringProperty(propertyName));
		}
		if (propertyName.equals(ENTITY_FIELDS)) {
			return validateFieldsList((ArrayList<DynamicEntityField>) getProperty(propertyName));
		}
		if (propertyName.equals(PK_FIELDS)) {
			return validatePrimaryKeyFieldsList((ArrayList<DynamicEntityField>) getProperty(propertyName));
		}
		return result;		
	}

	/**
	 * This method is intended for internal use only. It will be used to validate the correctness 
	 * of entity package in accordance with Java convention requirements. 
	 * This method will accept a null parameter. 
	 * 
	 * @param packName
	 * @return IStatus is the package name satisfies Java convention requirements
	 */
	private IStatus validateJavaPackage(String packName) {		
		if (packName == null || packName.equals(EMPTY_STRING)) {
			return new Status(
					IStatus.WARNING, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
					EclipseLinkUiMessages.DynamicEntityClassWizardPage_defaultPackageWarning);
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
		return Status.OK_STATUS;
	}

	/**
	 * This method is intended for internal use only. It will be used to validate 
	 * the validity of a specified mapping file and if a specified mapping file
	 * is included in the persistence unit.
	 * This method will accept a null parameter. 
	 * 
	 * @param xmlName
	 * @return IStatus is the mapping file exists or is the mapping file included
	 * in the persistence unit
	 */
	private IStatus validateXmlName(String xmlName) {
		IProject project = this.getTargetProject();
		if (project != null) {
			JpaXmlResource ormXmlResource = StringTools.stringIsEmpty(xmlName) ? null : getOrmXmlResource(xmlName);
			if (ormXmlResource == null) {
				return new Status(
						IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
						EclipseLinkUiMessages.DynamicEntityClassWizardPage_invalidXMLName);
			}
			else if (this.getJpaProject().getJpaFile(ormXmlResource.getFile()).getRootStructureNodesSize() == 0) {
				return new Status(
						IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
						EclipseLinkUiMessages.DynamicEntityClassWizardPage_xmlNotListedError);
			}
		}
		return Status.OK_STATUS;
	}

	protected JpaXmlResource getOrmXmlResource(String xmlName) {
		return getJpaProject()== null ? null : getJpaProject().getMappingFileXmlResource(new Path(xmlName));
	}

	/**
	 * This method is intended for internal use only. It will be used to validate
	 * the list of created dynamic entity fields to ensure there are no duplicates,
	 * to validate the validity and existence of a field's given attribute type, 
	 * and to validate the validity of the mapping types of the created fields.
	 * This method will accept a null parameter. 
	 * 
	 * @param list of DynamicEntityField
	 * @return IStatus are the dynamic entity field names unique
	 * 				   or is the dynamic entity field attribute type valid
	 * 				   or are the dynamic entity field mapping types valid
	 */
	private IStatus validateFieldsList(ArrayList<DynamicEntityField> fields) {
		if (fields != null && !fields.isEmpty()) {
			// Ensure there are no dynamic entity fields that have the same name in the table
			boolean hasDuplicates = hasDuplicatesInEntityFields(fields);
			if (hasDuplicates) {
				return new Status(
						IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
						EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_duplicateEntityFieldsError);
			}
			// Ensure ID and EmbeddedID mapping are not defined at the same time
			// and also ensure there's no multiple EmbeddedID mappings defined
			String errorMsg1 = checkInputFieldsMappingTypes(fields);
			if (errorMsg1 != null) {
				return new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, errorMsg1);
			}
			// Ensure that the given type of the dynamic entity fields in the table are valid
			String errorMsg2 = checkInputFieldsAttributeTypeValidity(fields);
			if (errorMsg2 != null) {
				return new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, errorMsg2);
			}
			// Ensure that the given type of the dynamic entity fields in the table exist
			String warningMsg = checkInputFieldsAttributeTypeExistence(fields);
			if (warningMsg != null) {
				return new Status(IStatus.WARNING, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, warningMsg);
			}
		}
		return Status.OK_STATUS;
	}

	private String checkInputFieldsMappingTypes(ArrayList<DynamicEntityField> fields) {
		IStatus validateFieldMappingTypeStatus = Status.OK_STATUS;
		Iterable<String> mappingKeys = this.getMappingKeys(fields);
		if (hasIDAndEmbeddedIDMappingDefined(mappingKeys)) {
			validateFieldMappingTypeStatus = new Status(IStatus.ERROR, JptJpaUiPlugin.PLUGIN_ID, 
					EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_bothIDAndEmbeddedIDDefinedError);
		} else if (hasMultipleEmbeddedIDMappings(mappingKeys)) {
			validateFieldMappingTypeStatus = new Status(IStatus.ERROR, JptJpaUiPlugin.PLUGIN_ID, 
					EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_multipleEmbeddedIDsDefinedError);
		}
		if (!validateFieldMappingTypeStatus.isOK()) {
			return validateFieldMappingTypeStatus.getMessage();
		}
		return null;
	}

	private Iterable<String> getMappingKeys(ArrayList<DynamicEntityField> fields) {
		return new TransformationIterable<DynamicEntityField, String>(fields) {
			@Override
			protected String transform(DynamicEntityField field) {
				return field.getMappingType().getKey();
			}
		};
	}

	private boolean hasIDAndEmbeddedIDMappingDefined(Iterable<String> mappingKeys) {
		return CollectionTools.contains(mappingKeys, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) 
				&& CollectionTools.contains(mappingKeys, MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
	}

	private boolean hasMultipleEmbeddedIDMappings(Iterable<String> mappingKeys) {
		int i = 0;
		for (String key : mappingKeys) {
			if (key == MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
				i++;
				if (i > 1) return true;
			}
		}
		return false;
	}

	private String checkInputFieldsAttributeTypeValidity(List<DynamicEntityField> fields) {
		IStatus validateFieldTypeStatus = Status.OK_STATUS;
		for (DynamicEntityField field: fields) {
			if (field.isKey() && !field.couldTypeBePKType()) {
				String message = MessageFormat.format(
						EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_invalidPKType, new Object[]{field.getFqnTypeName()});
				validateFieldTypeStatus = new Status(IStatus.ERROR,	JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);
				break;				
			}
			String sig = null;
			try {
				sig = Signature.createTypeSignature(field.getFqnTypeName(), true);
			} catch (IllegalArgumentException e) {
				String message = MessageFormat.format(EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_invalidArgument, new Object[]{e.getLocalizedMessage()});
				validateFieldTypeStatus = new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);
				break;
			}
			if (sig == null){
				validateFieldTypeStatus = JavaConventions.validateJavaTypeName(field.getType(), JavaCore.VERSION_1_5, JavaCore.VERSION_1_5);
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

	protected String checkInputFieldsAttributeTypeExistence(List<DynamicEntityField> fields) {
		IStatus validateFieldTypeStatus=Status.OK_STATUS;
		for (DynamicEntityField field: fields) {
			String sig = Signature.createTypeSignature(field.getFqnTypeName() ,true);
			if (sig == null) {
				String message = MessageFormat.format(
						EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_typeNotInProjectClasspath, new Object[]{field.getFqnTypeName()});
				validateFieldTypeStatus = new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);
				break;
			}
			int sigType = Signature.getTypeSignatureKind(sig);
			if (sigType == Signature.BASE_TYPE_SIGNATURE){
				continue;
			} else if (sigType == Signature.ARRAY_TYPE_SIGNATURE) {
				String elementSignature = Signature.getElementType(sig);
				if(Signature.getTypeSignatureKind(elementSignature) == Signature.BASE_TYPE_SIGNATURE){
					continue;
				}
				String qualifiedName = Signature.toString(elementSignature);
				IProject project = getTargetProject();
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
							EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_typeNotInProjectClasspath, new Object[]{field.getFqnTypeName()});
					validateFieldTypeStatus = new Status(IStatus.ERROR,
							JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);
					break;
				}
			} else {
				IProject project = getTargetProject();
				IJavaProject javaProject = JavaCore.create(project);
				IType type = null;
				try {
					type = javaProject.findType(field.getFqnTypeName());
				} catch (JavaModelException e) {
					validateFieldTypeStatus = e.getStatus();
					break;
				}
				if (type == null) {
					String message = MessageFormat.format(
							EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_typeNotInProjectClasspath, new Object[]{field.getFqnTypeName()});
					validateFieldTypeStatus = new Status(IStatus.ERROR,
							JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);
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
	 * This method is intended for internal use only. It provides a simple 
	 * algorithm for detecting dynamic entity fields that have duplicate names. 
	 * It will accept a null parameter and return boolean.
	 * 
	 * @param list of DynamicEntityField
	 * @return boolean true if duplicate fields exist; otherwise, false
	 */
	private boolean hasDuplicatesInEntityFields(ArrayList<DynamicEntityField> fields) {
		if (fields == null) {
			return false;
		}
		int n = fields.size();
		// nested for loops to check each element to see if other elements are the same
		for (int i = 0; i < n; i++) {
			DynamicEntityField field = fields.get(i);
			for (int j = i + 1; j < n; j++) {
				DynamicEntityField intEntity = fields.get(j);
				if (intEntity.getName().equals(field.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method is intended for internal use only. It will be used to validate if
	 * there are multiple dynamic entity field with ID mapping type defined and give
	 * the corresponding information if there are.
	 * It will accept a null parameter.
	 * 
	 * @param list of DynamicEntityField with ID mapping type
	 * @return boolean true if duplicate fields exist; otherwise, false
	 */
	private IStatus validatePrimaryKeyFieldsList(ArrayList<DynamicEntityField> pkFields) {
		if (pkFields.size() > 1) {
			return new Status(
					IStatus.INFO, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
					EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_applyEmbeddedIdMappingInfo);
		}
		return null;
	}

	/**
	 * This method is used to validate if a type with the same full qualified name as the dynamic
	 * entity exists. If a Java type or a static ORM type with the given name exists, a warning is given; 
	 * if a dynamic type with the given name exists, an error is given. Different message is 
	 * given if the given name and the existing one are same but with different cases.
	 * 
	 * Since source folder is not a consideration of dynamic entities, we will give a warning
	 * message if a Java type with the given full qualified name (package name + type name) exists
	 * regardless which source folder the Java type exists in.
	 * 
	 * @param packageName package name specified
	 * 		  typeName    class name specified
	 * @return IStatus does the type with the given full qualified name exists
	 */
	@Override
	protected IStatus canCreateTypeInClasspath(String packageName, String typeName) {
		String fullyQualifiedName = StringTools.EMPTY_STRING;
		if (!StringTools.stringIsEmpty(packageName)) {
			fullyQualifiedName = packageName + DOT + typeName;
		} else {
			fullyQualifiedName = typeName;
		}
		//Since the existence of a static ORM type depends on the corresponding Java type,
		//there is no need for a separate validation of it.
		for (String name : this.getJavaTypeNames()) {
			if (StringTools.stringsAreEqual(name, fullyQualifiedName)) {
				String message = NLS.bind(
						EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_typeExistsWarning, 
						new Object[] { fullyQualifiedName });
				return new Status(IStatus.WARNING, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);

			} else if (StringTools.stringsAreEqualIgnoreCase(name, fullyQualifiedName)) {
				String message = NLS.bind(
						EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_typeWithDiffCaseExistsWarning, 
						new Object[] { fullyQualifiedName });
				return new Status(IStatus.WARNING, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);
			}
		}
		PersistenceUnit pu = this.getPersistenceUnit();
		if (pu != null) {
			for (String name : ((EclipseLinkPersistenceUnit)this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames()) {
				if (StringTools.stringsAreEqual(name, fullyQualifiedName)) {
					String message = NLS.bind(
							EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_dynamicTypeExistsError, 
							new Object[] { fullyQualifiedName });
					return new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);

				} else if (StringTools.stringsAreEqualIgnoreCase(name, fullyQualifiedName)) {
					String message = NLS.bind(
							EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_dynamicTypeWithDiffCaseExistsError, 
							new Object[] { fullyQualifiedName });
					return new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, message);
				}
			}
		} else {
			return new Status(IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID, 
					EclipseLinkUiMessages.EclipseLinkDynamicEntityWizard_persistenceUnitNotFoundError);
		}
		return Status.OK_STATUS;
	}

	/**
	 * Returns the names of the given list of IType
	 */
	private Iterable<String> getJavaTypeNames() {
		return new TransformationIterable<IType, String>(this.getJavaTypes()) {
			@Override
			protected String transform(IType type) {
				return type.getFullyQualifiedName();
			}
		};
	}

	/**
	 * Returns all the Java types cross the select project
	 */
	protected List<IType> getJavaTypes() {
		IPackageFragmentRoot[] packRoots = this.getJavaPackageFragmentRoots();
		List<IType> typesList = new ArrayList<IType>();
		for (IPackageFragmentRoot root : packRoots) {
			try {
				IJavaElement[] jElements = root.getChildren();
				for (IJavaElement jElement : jElements) {
					if (jElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
						ICompilationUnit[] units = ((IPackageFragment) jElement).getCompilationUnits();
						for (ICompilationUnit unit : units) {
							CollectionTools.addAll(typesList, unit.getTypes());
						}
					}
				}
			} catch (JavaModelException e) {
				// fall through
				JptJpaEclipseLinkUiPlugin.log(e);
			}
		}	
		return typesList;
	}

	// ****************** misc ***********************

	protected JpaProject getJpaProject() {
		IProject project = getTargetProject();
		return ((project != null) && JpaFacet.isInstalled(project)) ?
				this.getJpaProject(project) :
					null;
	}

	protected JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}

	protected PersistenceUnit getPersistenceUnit() {
		Persistence p = this.getPersistence();
		if (p == null) {
			return null;
		}
		ListIterator<PersistenceUnit> units = p.getPersistenceUnits().iterator();
		return units.hasNext() ? units.next() : null;
	}

	protected Persistence getPersistence() {
		PersistenceXml pxml = this.getPersistenceXml();
		return (pxml == null) ? null : pxml.getPersistence();
	}

	protected PersistenceXml getPersistenceXml() {
		JpaRootContextNode rcn = this.getJpaProject().getRootContextNode();
		return (rcn == null) ? null : rcn.getPersistenceXml();
	}
}
