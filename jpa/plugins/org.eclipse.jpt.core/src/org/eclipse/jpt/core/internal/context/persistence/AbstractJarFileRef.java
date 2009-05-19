/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJarFileRef
	extends AbstractXmlContextNode
	implements JarFileRef
{
	protected XmlJarFileRef xmlJarFileRef;
	
	protected String fileName;
	
	protected JarFile jarFile;
	
	
	// **************** construction/initialization ****************************
	
	public AbstractJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		super(parent);
		this.xmlJarFileRef = xmlJarFileRef;
		this.fileName = xmlJarFileRef.getFileName();
		this.jarFile = this.buildJarFile();
	}
	
	protected JarFile buildJarFile() {
		if (StringTools.stringIsEmpty(this.fileName)) {
			return null;
		}
		JavaResourcePackageFragmentRoot jrpfr = this.getJpaProject().getJavaResourcePackageFragmentRoot(this.getFileName());
		return (jrpfr == null) ? null : this.buildJarFile(jrpfr);
	}
	
	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}
	
	
	// **************** file name **********************************************
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String newFileName) {
		String old = this.fileName;
		this.fileName = newFileName;
		this.xmlJarFileRef.setFileName(newFileName);
		this.firePropertyChanged(FILE_NAME_PROPERTY, old, newFileName);
	}
	
	protected void setFileName_(String newFileName) {
		String old = this.fileName;
		this.fileName = newFileName;
		this.firePropertyChanged(FILE_NAME_PROPERTY, old, newFileName);
	}
	
	
	// **************** JAR file ***********************************************
	
	public JarFile getJarFile() {
		return this.jarFile;
	}
	
	protected void setJarFile(JarFile jarFile) {
		JarFile old = this.jarFile;
		this.jarFile = jarFile;
		this.firePropertyChanged(JAR_FILE_PROPERTY, old, jarFile);
	}
	
	
	// **************** JarFileRef impl ****************************************
	
	public PersistentType getPersistentType(String typeName) {
		return (this.jarFile == null) ? null : this.jarFile.getPersistentType(typeName);
	}
	
	public boolean containsOffset(int textOffset) {
		return (this.xmlJarFileRef != null) && this.xmlJarFileRef.containsOffset(textOffset);
	}
	
	
	// **************** JpaStructureNode impl **********************************
	
	public String getId() {
		return PersistenceStructureNodes.JAR_FILE_REF_ID;
	}
	
	public IContentType getContentType() {
		return getParent().getContentType();
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
	
	
	// **************** XmlContextNode impl ************************************
	
	public TextRange getValidationTextRange() {
		return (this.xmlJarFileRef == null) ? null : this.xmlJarFileRef.getValidationTextRange();
	}
	
	
	// **************** updating ***********************************************

	public void update(XmlJarFileRef xjfr) {
		this.xmlJarFileRef = xjfr;
		this.setFileName_(xjfr.getFileName());
		this.updateJarFile();
	}

	protected void updateJarFile() {
		JavaResourcePackageFragmentRoot jrpfr = null;
		
		if (! StringTools.stringIsEmpty(this.fileName)) {
			
			// first, attempt to resolve location specifically
			jrpfr = javaPackageRoot_specifically();
			
			// then ... guess, basically
			if (jrpfr == null) {
				jrpfr = javaPackageRoot_guess();
			}
		}
		
		if (jrpfr == null) {
			if (this.jarFile != null) {
				this.jarFile.dispose();
				this.setJarFile(null);
			}
		} else { 
			if (this.jarFile == null) {
				this.setJarFile(this.buildJarFile(jrpfr));
			} else {
				this.jarFile.update(jrpfr);
			}
		}
	}
	
	private JavaResourcePackageFragmentRoot javaPackageRoot_specifically() {
		IProject project = getJpaProject().getProject();
		
		for (IPath deploymentPath : resolveDeploymentJarFilePath(new Path(this.fileName))) {
			IVirtualFile virtualJar = ComponentCore.createFile(project, deploymentPath);
			IFile realJar = virtualJar.getUnderlyingFile();
			if (realJar.exists() && realJar.getProject().equals(project)) {
				return getJpaProject().getJavaResourcePackageFragmentRoot(realJar.getProjectRelativePath().toString());
			}
		}
		
		return null;
	}
	
	private JavaResourcePackageFragmentRoot javaPackageRoot_guess() {
		String jarFileName = new Path(this.fileName).lastSegment();
		for (JpaFile jpaFile : CollectionTools.iterable(getJpaProject().jpaFiles())) {
			if (jpaFile.getFile().getName().equals(jarFileName)
					&& JptCorePlugin.JAR_CONTENT_TYPE.equals(jpaFile.getContentType())) {
				return (JavaResourcePackageFragmentRoot) jpaFile.getResourceModel();
			}
		}
		
		return null;
	}
	
	/**
	 * Return an array of deployment paths that may correspond
	 * to the given persistence.xml jar file entry
	 */
	protected abstract IPath[] resolveDeploymentJarFilePath(IPath jarFilePath);

	protected JarFile buildJarFile(JavaResourcePackageFragmentRoot jrpfr) {
		return this.getJpaFactory().buildJarFile(this, jrpfr);
	}
	
	
	// **************** validation *********************************************

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
	
	
	// **************** misc ***************************************************
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.getFileName());
	}
}
