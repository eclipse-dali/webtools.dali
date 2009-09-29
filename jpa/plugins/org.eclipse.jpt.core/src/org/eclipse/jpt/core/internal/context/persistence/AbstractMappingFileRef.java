/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.orm.MappingFileDefinition;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractMappingFileRef
	extends AbstractPersistenceXmlContextNode
	implements MappingFileRef2_0
{
	protected String fileName;

	protected MappingFile mappingFile;


	// ********** construction/initialization **********

	protected AbstractMappingFileRef(PersistenceUnit parent, String resourceFileName) {
		super(parent);
		this.fileName = resourceFileName;
		this.mappingFile = this.buildMappingFile();
	}

	protected MappingFile buildMappingFile() {
		JpaXmlResource xmlResource = this.getXmlResource();
		return (xmlResource == null) ? null : this.buildMappingFile(xmlResource);
	}
	
	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}

	
	// ********** JpaStructureNode implementation **********

	public String getId() {
		return PersistenceStructureNodes.MAPPING_FILE_REF_ID;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public void dispose() {
		if (this.mappingFile != null) {
			this.mappingFile.dispose();
		}
	}


	// ********** queries **********

	public MappingFilePersistenceUnitDefaults getPersistenceUnitDefaults() {
		MappingFileRoot root = this.getMappingFileRoot_();
		return (root == null) ? null : root.getPersistenceUnitDefaults();
	}

	/**
	 * #getMappingFileRoot() is already defined by JpaContextNode for the
	 * descendants of a "mapping file root" - we want something slightly
	 * different here...
	 */
	protected MappingFileRoot getMappingFileRoot_() {
		return (this.mappingFile == null) ? null : this.mappingFile.getRoot();
	}

	public boolean persistenceUnitDefaultsExists() {
		MappingFilePersistenceUnitDefaults defaults = this.getPersistenceUnitDefaults();
		return (defaults != null) && defaults.resourceExists();
	}

	public PersistentType getPersistentType(String typeName) {
		return (this.mappingFile == null) ? null : this.mappingFile.getPersistentType(typeName);
	}


	// ********** file name **********

	public String getFileName() {
		return this.fileName;
	}


	// ********** mapping file **********

	public MappingFile getMappingFile() {
		return this.mappingFile;
	}

	protected void setMappingFile(MappingFile mappingFile) {
		MappingFile old = this.mappingFile;
		this.mappingFile = mappingFile;
		this.firePropertyChanged(MAPPING_FILE_PROPERTY, old, mappingFile);
	}

	
	// ********** 2.0 static metamodel **********

	public void synchronizeStaticMetamodel() {
		if (this.mappingFile != null) {
			this.mappingFile.synchronizeStaticMetamodel();
		}
	}

	// ********** updating **********

	protected void update() {
		this.updateMappingFile();
	}

	protected void updateMappingFile() {
		JpaXmlResource xmlResource = this.getXmlResource();
		if (xmlResource != null) {
			if (this.mappingFile == null) {
				this.setMappingFile(this.buildMappingFile(xmlResource));
			} else {
				// if the resource type has changed, rebuild the mapping file
				if (this.mappingFile.getXmlResource() != xmlResource) {
					this.mappingFile.dispose();
					this.setMappingFile(this.buildMappingFile(xmlResource));
				} else {
					this.mappingFile.update(xmlResource);
				}
			}
		} else {
			if (this.mappingFile != null) {
				this.mappingFile.dispose();
				this.setMappingFile(null);
			}
		}
	}

	/**
	 * The XmlMappingFileRef resource is the Persistence xml resource.
	 * This returns the resource of the mapping file itself.
	 */
	protected JpaXmlResource getXmlResource() {
		return this.fileName == null ? null : this.getJpaProject().getMappingFileXmlResource(this.fileName);
	}
	
	protected MappingFile buildMappingFile(JpaXmlResource resource) {
		MappingFileDefinition mappingFileDef = (MappingFileDefinition) getJpaPlatform().getResourceDefinition(resource.getResourceType());
		return (mappingFileDef == null) ? null : mappingFileDef.getContextNodeFactory().buildMappingFile(this, resource);
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.mappingFile != null) {
			this.mappingFile.postUpdate();
		}
	}
	
	
	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (StringTools.stringIsEmpty(this.fileName)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE,
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		if (this.mappingFile == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.buildMissingMappingFileMessageID(),
					new String[] {this.fileName},
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		if (this.mappingFile.getRoot() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE,
					new String[] {this.fileName},
					this,
					this.getValidationTextRange()
				)
			);
		}

		this.mappingFile.validate(messages, reporter);
	}

	protected String buildMissingMappingFileMessageID() {
		return this.getPlatformFile().exists() ?
					JpaValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT
				:
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE;
	}

	protected IFile getPlatformFile() {
		return this.getJpaProject().convertToPlatformFile(this.fileName);
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.fileName);
	}

}
