/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.persistence;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.MappingFileRoot;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
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
	 * The mapping file corresponding to the ref's file name.
	 * This can be <code>null</code> if the name is invalid.
	 */
	protected MappingFile mappingFile;


	// ********** construction/initialization **********

	protected AbstractMappingFileRef(PersistenceUnit parent, String fileName) {
		super(parent);
		this.fileName = fileName;
		this.mappingFile = this.buildMappingFile();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncMappingFile();
	}

	@Override
	public void update() {
		super.update();
		this.updateMappingFile();
	}


	// ********** file name **********

	public String getFileName() {
		return this.fileName;
	}

	public boolean isFor(IFile file) {
		return (this.mappingFile != null) && file.equals(this.mappingFile.getXmlResource().getFile());
	}

	protected boolean isIn(IFolder folder) {
		return (this.mappingFile != null) && this.mappingFile.isIn(folder);
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

	protected void syncMappingFile() {
		if (this.mappingFile != null) {
			this.mappingFile.synchronizeWithResourceModel();
		}
	}

	protected void updateMappingFile() {
		JpaXmlResource newXmlResource = this.resolveMappingFileXmlResource();
		if (newXmlResource == null) {
			if (this.mappingFile != null) {
				this.mappingFile.dispose();
				this.setMappingFile(null);
			}
		} else {
			if (this.mappingFile == null) {
				this.setMappingFile(this.buildMappingFile(newXmlResource));
			} else {
				if (this.mappingFile.getXmlResource() == newXmlResource) {
					this.mappingFile.update();
				} else {
					// [seems like we should never get here; since if the file's
					// content type changed, the JPA project would return null...  ~bjv]
					// if the resource's content type has changed, we completely rebuild the mapping file
					this.mappingFile.dispose();
					this.setMappingFile(this.buildMappingFile(newXmlResource));
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
		JpaXmlResource xmlResource = this.getJpaProject().getMappingFileXmlResource(new Path(this.fileName));
		if (xmlResource == null) {
			return null;
		}
		if (xmlResource.isReverting()) {
			// 308254 - this can happen when orm.xml is closed without saving;
			// the model is completely whacked in another thread - so wipe our model(?)
			return null;
		}
		JptResourceType resourceType = xmlResource.getResourceType();
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


	// ********** misc **********

	public boolean persistenceUnitMetadataExists() {
		MappingFilePersistenceUnitMetadata metadata = this.getPersistenceUnitMetadata();
		return (metadata != null) && metadata.resourceExists();
	}

	public MappingFilePersistenceUnitMetadata getPersistenceUnitMetadata() {
		MappingFileRoot root = this.getChildMappingFileRoot();
		return (root == null) ? null : root.getPersistenceUnitMetadata();
	}

	/**
	 * The method {@link #getMappingFileRoot()} is already defined by
	 * {@link org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextNode}
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

	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.fileName);
	}

	public Iterable<? extends PersistentType> getPersistentTypes() {
		return (this.mappingFile != null) ? this.mappingFile.getPersistentTypes() : EmptyIterable.<JavaPersistentType>instance();
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
			messages.add(this.buildMappingFileValidationMessage());
			return;
		}

		this.mappingFile.validate(messages, reporter);
	}

	protected IMessage buildMappingFileValidationMessage() {
		int severity = IMessage.HIGH_SEVERITY;
		IFile file = this.getPlatformFile();
		if ( ! file.exists()) {
			return DefaultJpaValidationMessages.buildMessage(
					severity,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,
					new String[] {this.fileName},
					this,
					this.getValidationTextRange()
				);
		}
		String msgText = this.mappingFileContentIsUnsupported() ?
					JpaValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT :
					JpaValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE;
		return DefaultJpaValidationMessages.buildMessage(
				severity,
				msgText,
				new String[] {file.getName()},
				file
			);
	}

	protected IFile getPlatformFile() {
		return this.getJpaProject().getPlatformFile(new Path(this.fileName));
	}

	/**
	 * pre-condition: {@link #getPlatformFile()} exists
	 */
	protected boolean mappingFileContentIsUnsupported() {
		JpaXmlResource xmlResource = this.getJpaProject().getMappingFileXmlResource(new Path(this.fileName));
		return (xmlResource != null) && ! this.getJpaPlatform().supportsResourceType(xmlResource.getResourceType());
	}


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		return (this.mappingFile != null) ?
				this.mappingFile.createDeleteTypeEdits(type) :
				EmptyIterable.<DeleteEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return (this.mappingFile != null) ?
				this.mappingFile.createRenameTypeEdits(originalType, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.mappingFile != null) ?
				this.mappingFile.createMoveTypeEdits(originalType, newPackage) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.mappingFile != null) ?
				this.mappingFile.createRenamePackageEdits(originalPackage, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	public Iterable<ReplaceEdit> createRenameMappingFileEdits(IFile originalFile, String newName) {
		return this.isFor(originalFile) ?
				new SingleElementIterable<ReplaceEdit>(this.createRenameEdit(originalFile, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected abstract ReplaceEdit createRenameEdit(IFile originalFile, String newName);

	public Iterable<ReplaceEdit> createMoveMappingFileEdits(IFile originalFile, IPath runtineDestination) {
		return this.isFor(originalFile) ?
				new SingleElementIterable<ReplaceEdit>(this.createMoveEdit(originalFile, runtineDestination)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected abstract ReplaceEdit createMoveEdit(IFile originalFile, IPath runtineDestination);
}
