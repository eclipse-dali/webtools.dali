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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.resource.JpaXmlResourceProviderManager;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.JpaXmlResourceProvider;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.OrmXmlResource;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericMappingFileRef
	extends AbstractXmlContextNode
	implements MappingFileRef
{
	// this is null for an "implied" mapping file ref
	protected XmlMappingFileRef xmlMappingFileRef;

	protected String fileName;

	protected MappingFile mappingFile;


	// ********** construction/initialization **********

	public GenericMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		super(parent);
		this.initialize(xmlMappingFileRef);
	}

	protected void initialize(XmlMappingFileRef fileRef) {
		this.xmlMappingFileRef = fileRef;
		this.fileName = this.buildFileName();
		this.initializeMappingFile();
	}

	protected void initializeMappingFile() {
		JpaXmlResource xmlResource = this.getXmlResource();
		if (exists(xmlResource)) {
			this.mappingFile = this.buildMappingFile(xmlResource);
		}
	}

	protected JpaXmlResource getXmlResource() {
		JpaXmlResourceProvider xmlResourceProvider = this.getXmlResourceProvider();
		return (xmlResourceProvider == null) ? null : xmlResourceProvider.getXmlResource();
	}

	protected JpaXmlResourceProvider getXmlResourceProvider() {
		IFile platformFile = this.getPlatformFile();
		return exists(platformFile) ? getXmlResourceProvider(platformFile) : null;
	}

	protected IFile getPlatformFile() {
		if (this.fileName == null) {
			return null;
		}
		IProject project = this.getJpaProject().getProject();
		IPath deploymentPath = new Path(JptCorePlugin.getDeploymentURI(project, this.fileName));
		IVirtualFile vFile = ComponentCore.createFile(project, deploymentPath);
		return vFile.getUnderlyingFile();
	}

	protected static JpaXmlResourceProvider getXmlResourceProvider(IFile file) {
		return JpaXmlResourceProviderManager.instance().getXmlResourceProvider(file);
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return PersistenceStructureNodes.MAPPING_FILE_REF_ID;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.isVirtual() ? null : this.xmlMappingFileRef.getSelectionTextRange();
	}

	public void dispose() {
		if (this.mappingFile != null) {
			this.mappingFile.dispose();
		}
	}


	// ********** queries **********

	public boolean isVirtual() {
		return this.xmlMappingFileRef == null;
	}

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

	public boolean containsOffset(int textOffset) {
		return (this.xmlMappingFileRef != null) && this.xmlMappingFileRef.containsOffset(textOffset);
	}


	// ********** file name **********

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.xmlMappingFileRef.setFileName(fileName);
		this.setFileName_(fileName);
	}

	protected void setFileName_(String newFileName) {
		String old = this.fileName;
		this.fileName = newFileName;
		this.firePropertyChanged(FILE_NAME_PROPERTY, old, newFileName);
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


	// ********** updating **********

	public void update(XmlMappingFileRef mappingFileRef) {
		this.xmlMappingFileRef = mappingFileRef;
		this.setFileName_(this.buildFileName());
		this.updateMappingFile();
	}

	protected String buildFileName() {
		return this.isVirtual() ? JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH
						: this.xmlMappingFileRef.getFileName();
	}

	protected void updateMappingFile() {
		JpaXmlResource xmlResource = this.getXmlResource();
		if (exists(xmlResource)) {
			if (this.mappingFile == null) {
				this.setMappingFile(this.buildMappingFile(xmlResource));
			} else {
				// if the resource type has changed, rebuild the mapping file
				if (this.mappingFile.getXmlResource() != xmlResource) {
					this.mappingFile.dispose();
				}
				this.mappingFile.update(xmlResource);
			}
		} else {
			if (this.mappingFile != null) {
				this.mappingFile.dispose();
				this.setMappingFile(null);
			}
		}
	}

	protected MappingFile buildMappingFile(JpaXmlResource resource) {
		return this.getJpaPlatform().buildMappingFile(this, (OrmXmlResource) resource);
	}


	// ********** XmlContextNode implementation **********

	public TextRange getValidationTextRange() {
		return this.isVirtual() ? this.getPersistenceUnit().getValidationTextRange() :
		this.xmlMappingFileRef.getValidationTextRange();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);

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
			IFile platformFile = this.getPlatformFile();
			String msgID = exists(platformFile) ?
					JpaValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT
				:
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE;
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					msgID,
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

		this.mappingFile.validate(messages);
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.fileName);
	}

	/**
	 * convenience method - null check
	 */
	protected static boolean exists(IFile file) {
		return (file != null) && file.exists();
	}

	/**
	 * convenience method - null check
	 */
	protected static boolean exists(JpaXmlResource xmlResource) {
		return (xmlResource != null) && xmlResource.exists();
	}

}
