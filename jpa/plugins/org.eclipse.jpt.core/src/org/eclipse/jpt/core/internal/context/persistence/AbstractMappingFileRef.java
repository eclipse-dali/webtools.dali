/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>mapping-file</code> element
 */
public abstract class AbstractMappingFileRef
	extends AbstractPersistenceXmlContextNode
	implements MappingFileRef
{
	protected String fileName;

	/**
	 * the mapping file corresponding to the ref's file name;
	 * this can be null if the name is invalid
	 */
	protected MappingFile mappingFile;


	// ********** construction/initialization **********

	protected AbstractMappingFileRef(PersistenceUnit parent, String resourceFileName) {
		super(parent);
		this.fileName = resourceFileName;
		this.mappingFile = this.buildMappingFile();
	}


	// ********** overrides **********

	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.fileName);
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

	public boolean persistenceUnitDefaultsExists() {
		MappingFilePersistenceUnitDefaults defaults = this.getPersistenceUnitDefaults();
		return (defaults != null) && defaults.resourceExists();
	}

	public MappingFilePersistenceUnitDefaults getPersistenceUnitDefaults() {
		MappingFileRoot root = this.getChildMappingFileRoot();
		return (root == null) ? null : root.getPersistenceUnitDefaults();
	}

	/**
	 * The method {@link #getMappingFileRoot()} is already defined by
	 * {@link org.eclipse.jpt.core.internal.context.AbstractJpaContextNode}
	 * for getting what would be the "mapping file root" that <em>contains</em>
	 * the context node. We want something slightly different here: i.e. the
	 * "mapping file root" contained by the mapping file ref (since, actually,
	 * the mapping file ref is not even contained by a "mapping file root").
	 */
	protected MappingFileRoot getChildMappingFileRoot() {
		return (this.mappingFile == null) ? null : this.mappingFile.getRoot();
	}

	public PersistentType getPersistentType(String typeName) {
		return (this.mappingFile == null) ? null : this.mappingFile.getPersistentType(typeName);
	}


	// ********** file name **********

	public String getFileName() {
		return this.fileName;
	}

	public boolean isFor(IFile file) {
		return this.mappingFile != null && file.equals(this.mappingFile.getXmlResource().getFile());
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

	protected MappingFile buildMappingFile() {
		JpaXmlResource xmlResource = this.resolveMappingFileXmlResource();
		return (xmlResource == null) ? null : this.buildMappingFile(xmlResource);
	}

	protected void updateMappingFile() {
		JpaXmlResource xmlResource = this.resolveMappingFileXmlResource();
		
		if (xmlResource == null) {
			if (this.mappingFile != null) {
				this.mappingFile.dispose();
				this.setMappingFile(null);
			}
		} else {
			if (this.mappingFile == null) {
				this.setMappingFile(this.buildMappingFile(xmlResource));
			} else {
				if (this.mappingFile.getXmlResource() == xmlResource) {
					this.mappingFile.update();
				} else {
					// if the resource's content type has changed, we completely rebuild the mapping file
					this.mappingFile.dispose();
					this.setMappingFile(this.buildMappingFile(xmlResource));
				}
			}
		}
	}

	/**
	 * The mapping file ref resource is in the persistence xml resource
	 * (<code>persistence.xml</code>). This returns the resource of
	 * the mapping file itself (<code>orm.xml</code>).
	 */
	protected JpaXmlResource resolveMappingFileXmlResource() {
		if (this.fileName == null) {
			return null;
		}
		JpaXmlResource xmlResource = this.getJpaProject().getMappingFileXmlResource(this.fileName);
		if (xmlResource == null) {
			return null;
		}
		if (xmlResource.isReverting()) {
			// 308254 - this can happen when orm.xml is closed without saving;
			// the model is completely whacked in another thread - so wipe our model(?)
			return null;
		}
		JpaResourceType resourceType = xmlResource.getResourceType();
		if (resourceType == null) {
			return null;
		}
		if ( ! this.getJpaPlatform().supportsResourceType(resourceType)) {
			return null;
		}
		return xmlResource;
	}

	/**
	 * pre-condition: 'resource' is not null
	 */
	protected MappingFile buildMappingFile(JpaXmlResource resource) {
		return this.getJpaFactory().buildMappingFile(this, resource);
	}


	// ********** PersistentTypeContainer implementation **********

	public Iterable<? extends PersistentType> getPersistentTypes() {
		return (this.mappingFile != null) ? this.mappingFile.getPersistentTypes() : EmptyIterable.<JavaPersistentType>instance();
	}

	// ********** updating **********

	protected void update() {
		this.updateMappingFile();
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
					this.getValidationTextRange()));
			return;
		}
		
		if (this.mappingFile == null) {
			messages.add(buildMappingFileValidationMessage());
			return;
		}
		
		this.mappingFile.validate(messages, reporter);
	}
	
	protected IMessage buildMappingFileValidationMessage() {
		int severity = IMessage.HIGH_SEVERITY;
		IFile file = getPlatformFile();
		if (file.exists()) {
			JpaXmlResource xmlResource = getJpaProject().getMappingFileXmlResource(this.fileName);
			if (xmlResource != null 
					&& ! getJpaPlatform().supportsResourceType(xmlResource.getResourceType())) {
				return DefaultJpaValidationMessages.buildMessage(
					severity,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT,
					new String[] {file.getName()},
					file);
			}
			return DefaultJpaValidationMessages.buildMessage(
				severity,
				JpaValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE,
				new String[] {file.getName()},
				file);
		}
		return DefaultJpaValidationMessages.buildMessage(
			severity,
			JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,
			new String[] {this.fileName},
			this,
			getValidationTextRange());
	}
	
	protected IFile getPlatformFile() {
		return this.getJpaProject().convertToPlatformFile(this.fileName);
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		if (this.mappingFile != null) {
			return this.mappingFile.createDeleteTypeEdits(type);
		}
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createReplaceMappingFileEdits(IFile originalFile, String newName) {
		if (this.isFor(originalFile)) {
			return new SingleElementIterable<ReplaceEdit>(this.createReplaceEdit(originalFile, newName));
		}
		return EmptyIterable.instance();
	}

	protected abstract ReplaceEdit createReplaceEdit(IFile originalFile, String newName);

}
