/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>jar-file</code> element
 */
public abstract class AbstractJarFileRef
	extends AbstractPersistenceXmlContextNode
	implements JarFileRef
{
	protected final XmlJarFileRef xmlJarFileRef;

	protected String fileName;

	/**
	 * the jar file corresponding to the ref's file name;
	 * this can be null if the name is invalid
	 */
	protected JarFile jarFile;


	// ********** construction/initialization **********

	public AbstractJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		super(parent);
		this.xmlJarFileRef = xmlJarFileRef;
		this.fileName = xmlJarFileRef.getFileName();
		this.jarFile = this.buildJarFile();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setFileName_(this.xmlJarFileRef.getFileName());
		this.syncJarFile();
	}

	@Override
	public void update() {
		super.update();
		this.updateJarFile();
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return PersistenceStructureNodes.JAR_FILE_REF_ID;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public TextRange getSelectionTextRange() {
		return (this.xmlJarFileRef == null) ? null : this.xmlJarFileRef.getSelectionTextRange();
	}

	public void dispose() {
		if (this.jarFile != null) {
			this.jarFile.dispose();
		}
	}


	// ********** file name **********

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.setFileName_(fileName);
		this.xmlJarFileRef.setFileName(fileName);
	}

	/**
	 * We clear out {@link #jarFile} here because we cannot compare its file
	 * name to the ref's file name, since it may have been munged (see
	 * {@link #resolveJavaResourcePackageFragmentRoot_()}).
	 */
	protected void setFileName_(String fileName) {
		String old = this.fileName;
		this.fileName = fileName;
		if (this.firePropertyChanged(FILE_NAME_PROPERTY, old, fileName)) {
			// clear out the jar file here, it will be rebuilt during "update"
			if (this.jarFile != null) {
				this.jarFile.dispose();
				this.setJarFile(null);
			}
		}
	}


	// ********** jar file **********

	public JarFile getJarFile() {
		return this.jarFile;
	}

	protected void setJarFile(JarFile jarFile) {
		JarFile old = this.jarFile;
		this.jarFile = jarFile;
		this.firePropertyChanged(JAR_FILE_PROPERTY, old, jarFile);
	}

	protected JarFile buildJarFile() {
		JavaResourcePackageFragmentRoot jrpfr = this.resolveJavaResourcePackageFragmentRoot();
		return (jrpfr == null) ? null : this.buildJarFile(jrpfr);
	}

	/**
	 * If the file name changes during <em>sync</em>, the jar file will be
	 * cleared out in {@link #setFileName_(String)}. If we get here and the jar
	 * file is still present, we can <code>sync</code> it. Of course, it might
	 * still be obsolete if other things have changed....
	 * 
	 * @see #updateJarFile()
	 */
	protected void syncJarFile() {
		if (this.jarFile != null) {
			this.jarFile.synchronizeWithResourceModel();
		}
	}

	/**
	 * @see #syncJarFile()
	 */
	protected void updateJarFile() {
		JavaResourcePackageFragmentRoot jrpfr = this.resolveJavaResourcePackageFragmentRoot();
		if (jrpfr == null) {
			if (this.jarFile != null) {
				this.jarFile.dispose();
				this.setJarFile(null);
			}
		} else {
			if (this.jarFile == null) {
				this.setJarFile(this.buildJarFile(jrpfr));
			} else {
				if (this.jarFile.getJarResourcePackageFragmentRoot() == jrpfr) {
					this.jarFile.update();
				} else {
					this.jarFile.dispose();
					this.setJarFile(this.buildJarFile(jrpfr));
				}
			}
		}
	}

	protected JavaResourcePackageFragmentRoot resolveJavaResourcePackageFragmentRoot() {
		return StringTools.stringIsEmpty(this.fileName) ? null : this.resolveJavaResourcePackageFragmentRoot_();
	}

	/**
	 * pre-condition: 'fileName' is neither null nor empty
	 */
	protected JavaResourcePackageFragmentRoot resolveJavaResourcePackageFragmentRoot_() {
		// first, attempt to resolve location specifically...
		JavaResourcePackageFragmentRoot jrpfr = this.resolveJrpfrOnDeploymentPath();
		// ...then guess, basically
		return (jrpfr != null) ? jrpfr : this.resolveJrpfrBestMatch();
	}

	/**
	 * pre-condition: 'fileName' is neither null nor empty
	 */
	protected JavaResourcePackageFragmentRoot resolveJrpfrOnDeploymentPath() {
		for (IPath runtimePath : this.buildRuntimeJarFilePath(new Path(this.fileName))) {
			IVirtualFile virtualJar = ComponentCore.createFile(this.getProject(), runtimePath);
			IFile realJar = virtualJar.getUnderlyingFile();
			if (realJar.exists() && realJar.getProject().equals(this.getProject())) {
				return this.getJpaProject().getJavaResourcePackageFragmentRoot(realJar.getProjectRelativePath().toString());
			}
		}
		return null;
	}

	/**
	 * Return an array of runtime paths that may correspond
	 * to the given persistence.xml jar file entry
	 */
	protected IPath[] buildRuntimeJarFilePath(IPath jarFilePath) {
		IPath root = this.getJarRuntimeRootPath();
		return this.projectHasWebFacet() ?
				this.buildRuntimeJarFilePathWeb(root, jarFilePath) :
				this.buildRuntimeJarFilePathNonWeb(root, jarFilePath);
	}

	protected IPath getJarRuntimeRootPath() {
		return JptCorePlugin.getJarRuntimeRootPath(this.getProject());
	}

	protected boolean projectHasWebFacet() {
		return JptCorePlugin.projectHasWebFacet(this.getProject());
	}

	protected IPath[] buildRuntimeJarFilePathWeb(IPath root, IPath jarFilePath) {
		return new IPath[] {
				// first path entry assumes form "../lib/other.jar"
				root.append(jarFilePath.removeFirstSegments(1)),
				// second path entry assumes form of first, without ".." ("lib/other.jar")
				root.append(jarFilePath)
			};
	}

	protected IPath[] buildRuntimeJarFilePathNonWeb(IPath root, IPath jarFilePath) {
		return new IPath[] {
				// assumes form "../lib/other.jar"
				root.append(jarFilePath)
			};
	}

	protected IProject getProject() {
		return this.getJpaProject().getProject();
	}

	/**
	 * pre-condition: 'fileName' is neither null nor empty
	 */
	protected JavaResourcePackageFragmentRoot resolveJrpfrBestMatch() {
		String jarFileName = new Path(this.fileName).lastSegment();
		for (JpaFile jpaFile : this.getJpaProject().getJarJpaFiles()) {
			if (jpaFile.getFile().getName().equals(jarFileName)) {
				return (JavaResourcePackageFragmentRoot) jpaFile.getResourceModel();
			}
		}
		return null;
	}

	/**
	 * pre-condition: 'jrpfr' is not null
	 */
	protected JarFile buildJarFile(JavaResourcePackageFragmentRoot jrpfr) {
		return this.getContextNodeFactory().buildJarFile(this, jrpfr);
	}


	// ********** JarFileRef implementation **********

	public XmlJarFileRef getXmlJarFileRef() {
		return this.xmlJarFileRef;
	}

	public PersistentType getPersistentType(String typeName) {
		return (this.jarFile == null) ? null : this.jarFile.getPersistentType(typeName);
	}

	public boolean containsOffset(int textOffset) {
		return (this.xmlJarFileRef != null) && this.xmlJarFileRef.containsOffset(textOffset);
	}


	// ********** PersistentTypeContainer implementation **********

	public Iterable<? extends PersistentType> getPersistentTypes() {
		return (this.jarFile != null) ? this.jarFile.getPersistentTypes() : EmptyIterable.<JavaPersistentType>instance();
	}


	// ********** XmlContextNode implementation **********

	public TextRange getValidationTextRange() {
		return (this.xmlJarFileRef == null) ? null : this.xmlJarFileRef.getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createReplaceFolderEdits(IFolder originalFolder, String newName) {
		return this.isIn(originalFolder) ?
				new SingleElementIterable<ReplaceEdit>(this.createReplaceFolderEdit(originalFolder, newName)) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createReplaceFolderEdit(IFolder originalFolder, String newName) {
		return this.xmlJarFileRef.createReplaceFolderEdit(originalFolder, newName);
	}

	protected boolean isIn(IFolder folder) {
		return (this.jarFile != null) && this.jarFile.isIn(folder);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (StringTools.stringIsEmpty(this.xmlJarFileRef.getFileName())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_JAR_FILE,
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		messages.add(
			DefaultJpaValidationMessages.buildMessage(
				IMessage.NORMAL_SEVERITY,
				JpaValidationMessages.PERSISTENCE_UNIT_JAR_FILE_DEPLOYMENT_PATH_WARNING,
				this,
				this.getValidationTextRange()
			)
		);

		if (this.jarFile == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_JAR_FILE,
					new String[] {this.xmlJarFileRef.getFileName()},
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		this.jarFile.validate(messages, reporter);
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

}
