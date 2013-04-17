/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.operation;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.model.DynamicEntityField;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.model.EclipseLinkDynamicEntityTemplateModel;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.operation.NewEntityClassOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;

public class NewDynamicEntityClassOperation extends NewEntityClassOperation {

	public NewDynamicEntityClassOperation(IDataModel dataModel) {
		super(dataModel);
	}

	@Override
	public IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			this.generateUsingTemplates(monitor);
		} catch (Exception e) {
			return WTPCommonPlugin.createErrorStatus(e.toString());
		}
		return OK_STATUS;
	}

	protected void generateUsingTemplates(IProgressMonitor monitor) {
		// Create the entity template model
		EclipseLinkDynamicEntityTemplateModel tempModel = new EclipseLinkDynamicEntityTemplateModel(this.model);
		IProject project = this.getTargetProject();
		Command command = new AddDynamicEntityToXMLCommand(tempModel, project);
		this.run(new DaliRunnable(project.getWorkspace(), command));
	}

	// ********* add dynamic type mapping to XML command ***********

	protected static class AddDynamicTypeMappingToXMLCommand implements Command {
		protected final EclipseLinkDynamicEntityTemplateModel model;
		protected final IProject project;
		protected final String typeMappingKey;

		protected AddDynamicTypeMappingToXMLCommand(EclipseLinkDynamicEntityTemplateModel model, IProject project, String typeMappingKey) {
			super();
			this.model = model;
			this.project = project;
			this.typeMappingKey = typeMappingKey;
		}

		public void execute() {
			JpaProject jpaProject = this.getJpaProject();
			if (jpaProject == null) {
				return;
			}
			JptXmlResource xmlResource = this.getOrmXmlResource(jpaProject);
			EclipseLinkEntityMappings entityMappings = (EclipseLinkEntityMappings) jpaProject.
					getJpaFile(xmlResource.getFile()).getRootStructureNodes().iterator().next();
			EclipseLinkOrmPersistentType persistentType = (EclipseLinkOrmPersistentType) entityMappings.
					addPersistentType(this.typeMappingKey, this.model.getQualifiedJavaClassName());

			this.updatePersistentType(entityMappings, persistentType);

			try {
				xmlResource.saveIfNecessary();
			} catch (Exception e) {
				JptJpaEclipseLinkUiPlugin.instance().logError(e);
			}
		}

		protected void updatePersistentType(EclipseLinkEntityMappings entityMappings, EclipseLinkOrmPersistentType persistentType) {
			this.updateTypeMapping(persistentType.getMapping());
			setAccessType(entityMappings, persistentType);

			if (this.model.isCompositePK()) {
				EclipseLinkOrmPersistentType embeddable = (EclipseLinkOrmPersistentType) entityMappings.
						addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, this.model.getEmbeddableClassName());
				setAccessType(entityMappings, embeddable);
				persistentType.addVirtualAttribute(this.model.getDefaultEmbeddedIdName(),
						MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, embeddable.getName(), null);

				for (DynamicEntityField field : this.model.getPKFields()) {
					String attributeType = field.getFqnAttributeType();
					String name = field.getName();
					String mappingType = MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
					embeddable.addVirtualAttribute(name, mappingType, attributeType, null);
				}

				for (DynamicEntityField field : this.model.getEntityFields()) {
					if (field.getMappingType().getKey() != MappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
						persistentType.addVirtualAttribute(
								field.getName(), field.getMappingType().getKey(), 
								field.getFqnAttributeType(), field.getFqnTargetType()
								);
					}
				}
			} else {
				for (DynamicEntityField field : this.model.getEntityFields()) {
					persistentType.addVirtualAttribute(
							field.getName(), field.getMappingType().getKey(), 
							field.getFqnAttributeType(), field.getFqnTargetType()
							);
				}
			}
		}

		private void setAccessType(EclipseLinkEntityMappings entityMappings,
				EclipseLinkOrmPersistentType persistentType) {
			if (entityMappings.getAccess() != EclipseLinkAccessType.VIRTUAL) {
				persistentType.setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
			}
		}

		protected void updateTypeMapping(EclipseLinkOrmTypeMapping typeMapping) {
			// do nothing
		}

		protected JptXmlResource getOrmXmlResource(JpaProject jpaProject) {
			return this.model.isMappingXMLDefault() ?
					jpaProject.getDefaultOrmXmlResource() :
					jpaProject.getMappingFileXmlResource(new Path(this.model.getMappingXMLName()));
		}

		protected JpaProject getJpaProject() {
			return (JpaProject) this.project.getAdapter(JpaProject.class);
		}
	}

	// ********* add dynamic entity to XML command ***********

	protected static class AddDynamicEntityToXMLCommand extends AddDynamicTypeMappingToXMLCommand {
		protected AddDynamicEntityToXMLCommand(EclipseLinkDynamicEntityTemplateModel model, IProject project) {
			super(model, project, MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		}

		@Override
		protected void updateTypeMapping(EclipseLinkOrmTypeMapping typeMapping) {
			super.updateTypeMapping(typeMapping);
			Entity entity = (Entity) typeMapping;
			if (this.model.isEntityNameSet()) {
				entity.setSpecifiedName(this.model.getEntityName());
			}
			if (this.model.isTableNameSet()) {
				entity.getTable().setSpecifiedName(this.model.getTableName());
			}
		}
	}
}
