/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class CreateEntityTemplateModel {
	
	protected IDataModel dataModel;
	
	private static final String DOT = "."; //$NON-NLS-1$
	private static final String BRACKET = "["; //$NON-NLS-1$
	private static final String PK_SUFFIX = "PK"; //$NON-NLS-1$
	private static final String QUALIFIED_SERIALIZABLE = "java.io.Serializable"; //$NON-NLS-1$
	private static final String PERSISTENCE_PACKAGE = "javax.persistence.*"; //$NON-NLS-1$
	private static final String ENTITY_ANNOTATION = "@Entity"; //$NON-NLS-1$
	private static final String MAPPED_AS_SUPERCLASS_TYPE = "@MappedSuperclass"; //$NON-NLS-1$
	private static final String INHERITANCE_TYPE = "@Inheritance"; //$NON-NLS-1$	
		
	/**
	 * Constructs entity model as expansion of the data model
	 * @param dataModel
	 */
	public CreateEntityTemplateModel(IDataModel dataModel) {
		this.dataModel = dataModel;
	}
	
	/**
	 * Returns the necessary imports on depends of entity (primary keys) fields. It is used from 
	 * JET emmiter when it generates entity (IdClass)
	 * @param isIdClass flag, which indicates the case. When it is false, the result is 
	 * the import list for the entity class, in other case the results is the set for the IdClass 
	 * generation
	 * @return the imports collection with the imports for the generated java class
	 */
	public Collection<String> getImports(boolean isIdClass) {
		Collection<String> collection = new TreeSet<String>();
		
		String className = getClassName();
		String superclassName = getQualifiedSuperclassName();

		if (superclassName != null && superclassName.length() > 0 && 
				!equalSimpleNames(className, superclassName)) {
			collection.add(superclassName);
		}
		
		List interfaces = getQualifiedInterfaces();
		if (interfaces != null) {
			Iterator iterator = interfaces.iterator();
			while (iterator.hasNext()) {
				String iface = (String) iterator.next();
				if (!equalSimpleNames(getClassName(), iface)) {
					collection.add(iface);
				}
			}
		}
		if (isIdClass) {
			collection.addAll(getIdClassImportList());
		} else {			
			collection.add(PERSISTENCE_PACKAGE);
			collection.addAll(getFieldImportList());
			
		}
		return collection;
	}	

	/**
	 * @return class name of the entity
	 */
	public String getClassName() {
		return getProperty(INewJavaClassDataModelProperties.CLASS_NAME).trim();
	}

	/**
	 * @return package name when the entity will be generated
	 */
	public String getJavaPackageName() {
		return getProperty(INewJavaClassDataModelProperties.JAVA_PACKAGE).trim();
	}

	/**
	 * @return fully qualified java class name
	 */
	public String getQualifiedJavaClassName() {
		if (!getJavaPackageName().equals(IEntityDataModelProperties.EMPTY_STRING)) {
			return getJavaPackageName() + DOT + getClassName();
		} 
		return getClassName();
	}

	/**
	 * @return the name 
	 */
	public String getSuperclassName() {
		String qualified = getQualifiedSuperclassName();
		if (equalSimpleNames(getClassName(), qualified)) {
			return qualified;
		} else {
			return Signature.getSimpleName(qualified);
		}
	}
	
	/**
	 * @return fully qualified name of the entity's super class
	 */
	public String getQualifiedSuperclassName() {
		return getProperty(INewJavaClassDataModelProperties.SUPERCLASS).trim();
	}
	
	/**
	 * @return list with the interfaces implemented from entity class 
	 */
	public List getInterfaces() {
		List qualifiedInterfaces = getQualifiedInterfaces();
		List interfaces = new ArrayList(qualifiedInterfaces.size());
		
		Iterator iter = qualifiedInterfaces.iterator();
		while (iter.hasNext()) {
			String qualified = (String) iter.next();
			if (equalSimpleNames(getClassName(), qualified)) {
				interfaces.add(qualified);
			} else {
				interfaces.add(Signature.getSimpleName(qualified));
			}
		}
		
		return interfaces;
	}

	/**
	 * @return list with the interfaces (fully qualified named) implemented from entity class
	 */
	public List getQualifiedInterfaces() {
		List interfaces = (List) this.dataModel.getProperty(INewJavaClassDataModelProperties.INTERFACES);		
		if (interfaces == null){
			interfaces = new ArrayList();
		} 
		interfaces.add(QUALIFIED_SERIALIZABLE);
		return interfaces;
	}

	/**
	 * Returns the value of the specified string property
	 * @param propertyName
	 * @return string value of teh specified propert
	 */
	protected String getProperty(String propertyName) {
		return dataModel.getStringProperty(propertyName);
	}
	
	/**
	 * This methods is used for the comparison of fully qualified types 
	 * @param name1 first type name
	 * @param name2 second type name
	 * @return whether the simple names of the types are equal
	 */
	protected boolean equalSimpleNames(String name1, String name2) {
		String simpleName1 = Signature.getSimpleName(name1);
		String simpleName2 = Signature.getSimpleName(name2);
		return simpleName1.equals(simpleName2);
	}
	
	/**
	 * @return the type of the artifact - Entity or Mapped superclass
	 */
	public String getArtifactType() {
		if(dataModel.getBooleanProperty(IEntityDataModelProperties.MAPPED_AS_SUPERCLASS)) {
			return MAPPED_AS_SUPERCLASS_TYPE;
		} 
		return ENTITY_ANNOTATION;
	}

	/**
	 * @return whether entity set inheritance strategy
	 */
	public boolean isInheritanceSet() {
		return dataModel.getBooleanProperty(IEntityDataModelProperties.INHERITANCE);
	}
	
	/**
	 * @return the name of the inheritance strategy, as it is defined in the specification
	 */
	public String getInheritanceStrategyName() {		
		return getProperty(IEntityDataModelProperties.INHERITANCE_STRATEGY);
	}
	
	/**
	 * @return the constructed @Inheritance annotation with the relevant strategy
	 * if it is chosen
	 */
	public String getInheritanceStrategy() {
		String result = IEntityDataModelProperties.EMPTY_STRING;
		if (isInheritanceSet()) {	
			result = INHERITANCE_TYPE;
			if (!getProperty(IEntityDataModelProperties.INHERITANCE_STRATEGY).equals(IEntityDataModelProperties.EMPTY_STRING)) { //$NON-NLS-1$
				result += "(strategy=InheritanceType." + getProperty(IEntityDataModelProperties.INHERITANCE_STRATEGY) + ")"; //$NON-NLS-1$ $NON-NLS-2$
			
			}		
		}
		return result;
	}	
	
	/**
	 * @return whether the generated artifact is not entity 
	 */
	public boolean isNonEntitySuperclass() {
		return !dataModel.getBooleanProperty(IEntityDataModelProperties.ENTITY);
	}

	/**
	 * @return true the created artifact will be annotated
	 * @return false the entity mappings will be registered in XML
	 */
	public boolean isArtifactsAnnotated() {
		return !dataModel.getBooleanProperty(IEntityDataModelProperties.XML_SUPPORT);
	}
	
	public boolean isMappingXMLDefault() {
		if (getMappingXMLName().equals(IEntityDataModelProperties.EMPTY_STRING)) {
			return true;
		}
		return getMappingXMLName().equals(JptCorePlugin.getDefaultOrmXmlDeploymentURI(getProject()));
	}
	
	public String getMappingXMLName() {
		return dataModel.getStringProperty(IEntityDataModelProperties.XML_NAME).trim();
	}
	
	public IFile getMappingXmlFile() {
		IFile ormFile = null;
		IProject project = getProject();
		IPackageFragmentRoot[] sourceFragments = J2EEProjectUtilities.getSourceContainers(project);
		for (IPackageFragmentRoot packageFragmentRoot : sourceFragments) {
			ormFile = project.getFile(packageFragmentRoot.getResource().getName() + File.separator + getMappingXMLName());
			if (ormFile.exists()) {
				break;
			}
		}		
		return ormFile;
	}
	
	/**
	 * @return the entity name (could be different from the class name)
	 * See <code>isEntityNameSet()<code>
	 */
	public String getEntityName() {
		return getProperty(IEntityDataModelProperties.ENTITY_NAME).trim();
	}
	
	/**
	 * @return whether the entity name is different than class name
	 */
	public boolean isEntityNameSet() {
		boolean result = false;
		if (!getClassName().equals(getEntityName())) {
			result = true;
		}
		return result;
	}
	
	/**
	 * @return whether the table name is specified explicitly
	 */
	public boolean isTableNameSet() {
		return !dataModel.getBooleanProperty(IEntityDataModelProperties.TABLE_NAME_DEFAULT);
	}
	
	/**
	 * @return the table name (if it is specified)
	 * See <code>isTableNameSet()<code>
	 */
	public String getTableName() {
		return getProperty(IEntityDataModelProperties.TABLE_NAME).trim();
	}

	/**
	 * @return list with the entity fields
	 */
	public List<EntityRow> getEntityFields() {
		ArrayList<EntityRow> fields = (ArrayList<EntityRow>) dataModel.getProperty(IEntityDataModelProperties.ENTITY_FIELDS);
		if (fields == null){
			return new ArrayList<EntityRow>();
		} else
			return fields;
	}

	/**
	 * @return list with the imports necessary for the entity (based on its fields)
	 */
	public List<String> getFieldImportList() {
		List<String> imports = new ArrayList<String>();
		List<EntityRow> entities = getEntityFields();
		for (EntityRow entityRow : entities) {
			if (!imports.contains(entityRow.getFqnTypeName()) && !entityRow.getType().equals(entityRow.getFqnTypeName())) {
				String fqnTypeName = entityRow.getFqnTypeName();
				//remove the array brackets [] for the java.lang.Byte[] & java.lang.Character[]
				if (fqnTypeName.indexOf(BRACKET) != -1) {
					fqnTypeName = fqnTypeName.substring(0, fqnTypeName.indexOf("["));
				}
				imports.add(fqnTypeName);
			}
		}
		return imports;		
	}
	/**
	 * @return list with the imports necessary for the id class (based on its fields - primary keys of the entity)
	 */
	public List<String> getIdClassImportList() {
		List<String> imports = new ArrayList<String>();
		List<EntityRow> entities = getEntityFields();
		List<String> pkFields = getPKFields();
		for (EntityRow entityRow : entities) {
			String name = entityRow.getName();
			if (pkFields.contains(name)) {			
				if (!imports.contains(entityRow.getFqnTypeName()) && !entityRow.getType().equals(entityRow.getFqnTypeName())) {
					imports.add(entityRow.getFqnTypeName());
				}
			}
		}
		return imports;		
	}
	
	/**
	 * @return whether the access type is field based
	 */
	public boolean isFieldAccess() {
		return dataModel.getBooleanProperty(IEntityDataModelProperties.FIELD_ACCESS_TYPE);
	}	
	
	/**
	 * @return the primary key is composite (more than one annotated as primary key field)
	 */
	public boolean isCompositePK() {
		return getPKFields().size() > 1;
	}

	/**
	 * @return list with primary key name(s)
	 */
	public List<String> getPKFields() {
		return (ArrayList<String>)dataModel.getProperty(IEntityDataModelProperties.PK_FIELDS);
	}	
	
	/**
	 * @return constructed name of the id class (entity name + PK as suffix)
	 */
	public String getIdClassName() {
		return getClassName() + PK_SUFFIX;
	}
	
	/**
	 * @return IProject presentation of JPA project
	 */
	public IProject getProject() {
		String projectName = dataModel.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME);
		return ProjectUtilities.getProject(projectName);
	}	

	
}
