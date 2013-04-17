/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLinkDynamicEntityTemplateModel {

	protected IDataModel dataModel;

	private static final String DOT = "."; //$NON-NLS-1$
	private static final String PK_SUFFIX = "PK"; //$NON-NLS-1$
	private static final String DEFAULT_EMBEDDED_ID_NAME = "id"; //$NON-NLS-1$

	/**
	 * Constructs entity model as expansion of the data model
	 * @param dataModel
	 */
	public EclipseLinkDynamicEntityTemplateModel(IDataModel dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * @return class name of the dynamic entity
	 */
	public String getClassName() {
		return getProperty(INewJavaClassDataModelProperties.CLASS_NAME).trim();
	}

	/**
	 * @return package name when the dynamic entity will be generated
	 */
	public String getJavaPackageName() {
		return getProperty(INewJavaClassDataModelProperties.JAVA_PACKAGE).trim();
	}

	/**
	 * @return fully qualified Java class name
	 */
	public String getQualifiedJavaClassName() {
		if (!getJavaPackageName().equals(IEntityDataModelProperties.EMPTY_STRING)) {
			return getJavaPackageName() + DOT + getClassName();
		}
		return getClassName();
	}

	/**
	 * Returns the value of the specified string property
	 * @param propertyName
	 * @return string value of the specified property
	 */
	protected String getProperty(String propertyName) {
		return dataModel.getStringProperty(propertyName);
	}

	public boolean isMappingXMLDefault() {
		if (getMappingXMLName().equals(IEntityDataModelProperties.EMPTY_STRING)) {
			return true;
		}
		return getMappingXMLName().equals(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
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
	 * @return the dynamic entity name (could be different from the class name)
	 * See <code>isEntityNameSet()<code>
	 */
	public String getEntityName() {
		return getProperty(IEntityDataModelProperties.ENTITY_NAME).trim();
	}

	/**
	 * @return whether the dynamic entity name is different than class name
	 */
	public boolean isEntityNameSet() {
		boolean result = false;
		if (!StringTools.isBlank(getEntityName()) && !getClassName().equals(getEntityName())) {
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
	 * @return list of the dynamic entity fields
	 */
	public List<DynamicEntityField> getEntityFields() {
		ArrayList<DynamicEntityField> fields = (ArrayList<DynamicEntityField>) dataModel.getProperty(IEntityDataModelProperties.ENTITY_FIELDS);
		if (fields == null){
			return new ArrayList<DynamicEntityField>();
		} else
			return fields;
	}

	/**
	 * @return the primary key is composite (more than one set as primary key field)
	 */
	public boolean isCompositePK() {
		return getPKFields().size() > 1;
	}

	/**
	 * @return list of primary key fields
	 */
	public List<DynamicEntityField> getPKFields() {
		return (ArrayList<DynamicEntityField>) dataModel.getProperty(IEntityDataModelProperties.PK_FIELDS);
	}

	/**
	 * @return constructed name of the mapping file embeddable class (package name + entity name + PK as suffix)
	 */
	public String getEmbeddableClassName() {
		return getQualifiedJavaClassName() + PK_SUFFIX;
	}

	/**
	 * @return constructed the default name of the embedded id mapping
	 */
	public String getDefaultEmbeddedIdName() {
		return DEFAULT_EMBEDDED_ID_NAME;
	}

	/**
	 * @return IProject presentation of JPA project
	 */
	public IProject getProject() {
		String projectName = dataModel.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME);
		return ProjectUtilities.getProject(projectName);
	}
}
