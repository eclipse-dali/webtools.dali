/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.persistence;

import java.util.Collection;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>mapping-file</code> element
 */
public abstract class AbstractMappingFileRef<MF extends MappingFile>
	extends AbstractPersistenceXmlContextModel<PersistenceUnit>
	implements MappingFileRef
{
	protected String fileName;

	/**
	 * The mapping file corresponding to the ref's file name.
	 * This can be <code>null</code> if the name is invalid.
	 */
	protected MF mappingFile;


	// ********** construction/initialization **********

	protected AbstractMappingFileRef(PersistenceUnit parent, String fileName) {
		super(parent);
		this.fileName = fileName;
		this.mappingFile = this.buildMappingFile();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncMappingFile(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateMappingFile(monitor);
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.mappingFile != null) {
			this.mappingFile.addRootStructureNodesTo(jpaFile, rootStructureNodes);
		}
	}


	// ********** file name **********

	public String getFileName() {
		return this.fileName;
	}

	protected boolean isIn(IFolder folder) {
		return (this.mappingFile != null) && this.mappingFile.isIn(folder);
	}


	// ********** mapping file **********

	public MappingFile getMappingFile() {
		return this.mappingFile;
	}

	protected void setMappingFile(MF mappingFile) {
		MappingFile old = this.mappingFile;
		this.mappingFile = mappingFile;
		this.firePropertyChanged(MAPPING_FILE_PROPERTY, old, mappingFile);
	}

	protected abstract MF buildMappingFile();

	protected void syncMappingFile(IProgressMonitor monitor) {
		this.syncMappingFile(true, monitor);
	}

	/**
	 * We call this method from both {@link #syncMappingFile(IProgressMonitor)} and
	 * {@link #updateMappingFile(IProgressMonitor)} because<ul>
	 * <li>a <em>sync</em> will occur when the file is edited directly;
	 *     and the user could modify something (e.g. the version number) that
	 *     triggers the file being "resolved" or not;
	 *     see {@link #resolveResourceMappingFile()}
	 * <li>an <em>update</em> will occur whenever the entire file is added or
	 *     removed
	 * </ul>
	 */
	protected void syncMappingFile(boolean sync, IProgressMonitor monitor) {
		Object newResourceMappingFile = this.resolveResourceMappingFile();
		if (newResourceMappingFile == null) {
			this.setMappingFile(null);
		} else {
			if (this.mappingFile == null) {
				this.setMappingFile(this.buildMappingFile(newResourceMappingFile));
			} else {
				if (this.mappingFile.getResourceMappingFile() == newResourceMappingFile) {
					if (sync) {
						this.mappingFile.synchronizeWithResourceModel(monitor);
					} else {
						this.mappingFile.update(monitor);
					}
				} else {
					// [seems like we should never get here; since if the file's
					// content type changed, the JPA project would return null...  ~bjv]
					// [I have hit this before (had this println uncommmented), 
					// but I am not sure how... ~kfb]
					// [I hit this by deleting the implied orm.xml file and then adding it back, I think 
					// it will not be possible to hit it now that I null out the mappingFile in dispose() ~kfb]
					// System.out.println("AbstractMappingFileRef.syncMappingFile");
					// if the resource's content type has changed, we completely rebuild the mapping file
					this.setMappingFile(this.buildMappingFile(newResourceMappingFile));
				}
			}
		}
	}

	protected abstract Object resolveResourceMappingFile();

	/**
	 * pre-condition: 'resourceMappingFile' is not <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	protected MF buildMappingFile(Object resourceMappingFile) {
		return (MF) this.getJpaFactory().buildMappingFile(this, resourceMappingFile);
	}

	protected void updateMappingFile(IProgressMonitor monitor) {
		this.syncMappingFile(false, monitor);
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<MappingFileRef> getStructureType() {
		return MappingFileRef.class;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public Iterable<JpaStructureNode> getStructureChildren() {
		return IterableTools.emptyIterable();
	}

	public int getStructureChildrenSize() {
		return 0;
	}


	// ********** queries/generators **********

	public Iterable<Query> getMappingFileQueries() {
		return (this.mappingFile != null) ? this.mappingFile.getMappingFileQueries() : EmptyIterable.<Query>instance();
	}

	public Iterable<Generator> getMappingFileGenerators() {
		return (this.mappingFile != null) ? this.mappingFile.getMappingFileGenerators() : EmptyIterable.<Generator>instance();
	}


	// ********** misc **********

	public boolean persistenceUnitMetadataExists() {
		MappingFilePersistenceUnitMetadata metadata = this.getPersistenceUnitMetadata();
		return (metadata != null) && metadata.resourceExists();
	}

	public MappingFilePersistenceUnitMetadata getPersistenceUnitMetadata() {
		MappingFile.Root root = this.getChildMappingFileRoot();
		return (root == null) ? null : root.getPersistenceUnitMetadata();
	}

	/**
	 * The method {@link #getMappingFileRoot()} is already defined by
	 * {@link org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel}
	 * for getting what would be the "mapping file root" that <em>contains</em>
	 * the context node. We want something slightly different here: i.e. the
	 * "mapping file root" contained by the mapping file ref (since, actually,
	 * the mapping file ref is not even contained by a "mapping file root").
	 */
	protected MappingFile.Root getChildMappingFileRoot() {
		return (this.mappingFile == null) ? null : this.mappingFile.getRoot();
	}

	public PersistentType getPersistentType(String typeName) {
		return (this.mappingFile == null) ? null : this.mappingFile.getPersistentType(typeName);
	}

	public Iterable<? extends PersistentType> getPersistentTypes() {
		return (this.mappingFile != null) ? this.mappingFile.getPersistentTypes() : EmptyIterable.<PersistentType>instance();
	}

	public Iterable<? extends ManagedType> getManagedTypes() {
		return (this.mappingFile != null) ? this.mappingFile.getManagedTypes() : EmptyIterable.<ManagedType>instance();
	}

	public ManagedType getManagedType(String typeName) {
		return (this.mappingFile == null) ? null : this.mappingFile.getManagedType(typeName);
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.fileName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (StringTools.isBlank(this.fileName)) {
			messages.add(
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE
				)
			);
			return;
		}

		if (this.mappingFile == null) {
			messages.add(this.buildMissingMappingFileValidationMessage());
			return;
		}

		this.mappingFile.validate(messages, reporter);
	}

	protected IMessage buildMissingMappingFileValidationMessage() {
		IFile file = this.getPlatformFile();
		if ( ! file.exists()) {
			return this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,
					this.fileName
				);
		}
		ValidationMessage msg = this.mappingFileContentIsUnsupported() ?
					JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT :
					JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE;
		return ValidationMessageTools.buildValidationMessage(file, msg, file.getName());
	}

	protected IFile getPlatformFile() {
		return this.getJpaProject().getPlatformFile(new Path(this.fileName));
	}

	/**
	 * pre-condition: {@link #getPlatformFile()} exists
	 */
	protected abstract boolean mappingFileContentIsUnsupported();


	// ********** refactoring **********

	public Iterable<DeleteEdit> createDeleteTypeEdits(IType type) {
		return (this.mappingFile != null) ?
				this.mappingFile.createDeleteTypeEdits(type) :
				IterableTools.<DeleteEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return (this.mappingFile != null) ?
				this.mappingFile.createRenameTypeEdits(originalType, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.mappingFile != null) ?
				this.mappingFile.createMoveTypeEdits(originalType, newPackage) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.mappingFile != null) ?
				this.mappingFile.createRenamePackageEdits(originalPackage, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenameMappingFileEdits(IFile originalFile, String newName) {
		return this.isFor(originalFile) ?
				IterableTools.singletonIterable(this.createRenameEdit(originalFile, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected abstract ReplaceEdit createRenameEdit(IFile originalFile, String newName);

	public Iterable<ReplaceEdit> createMoveMappingFileEdits(IFile originalFile, IPath runtineDestination) {
		return this.isFor(originalFile) ?
				IterableTools.singletonIterable(this.createMoveEdit(originalFile, runtineDestination)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected abstract ReplaceEdit createMoveEdit(IFile originalFile, IPath runtineDestination);

	protected boolean isFor(IFile file) {
		return (this.mappingFile != null) && file.equals(this.mappingFile.getResource());
	}
}
