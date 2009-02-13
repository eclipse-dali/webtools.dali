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

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.jar.JarFile;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericJarFileRef
	extends AbstractXmlContextNode
	implements JarFileRef
{
	protected XmlJarFileRef xmlJarFileRef;

	protected JarFile jarFile;


	// ********** construction/initialization **********

	public GenericJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		super(parent);
		this.xmlJarFileRef = xmlJarFileRef;
		this.jarFile = this.buildJarFile();
	}

	protected JarFile buildJarFile() {
		JarResourcePackageFragmentRoot jrpfr = this.getJpaProject().getJarResourcePackageFragmentRoot(this.getJarFileName());
		return (jrpfr == null) ? null : this.buildJarFile(jrpfr);
	}
	
	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}

	
	// ********** JpaStructureNode implementation **********

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


	// ********** queries **********

	public String getJarFileName() {
		return this.xmlJarFileRef.getFileName();
	}

	public PersistentType getPersistentType(String typeName) {
		return (this.jarFile == null) ? null : this.jarFile.getPersistentType(typeName);
	}

	public boolean containsOffset(int textOffset) {
		return (this.xmlJarFileRef != null) && this.xmlJarFileRef.containsOffset(textOffset);
	}


	// ********** JAR file **********

	public JarFile getJarFile() {
		return this.jarFile;
	}

	protected void setJarFile(JarFile jarFile) {
		JarFile old = this.jarFile;
		this.jarFile = jarFile;
		this.firePropertyChanged(JAR_FILE_PROPERTY, old, jarFile);
	}


	// ********** updating **********

	public void update(XmlJarFileRef xjfr) {
		this.xmlJarFileRef = xjfr;
		this.updateJarFile();
	}

	protected void updateJarFile() {
		JarResourcePackageFragmentRoot jrpfr = this.getJpaProject().getJarResourcePackageFragmentRoot(this.getJarFileName());
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

	protected JarFile buildJarFile(JarResourcePackageFragmentRoot jrpfr) {
		return this.getJpaFactory().buildJarFile(this, jrpfr);
	}


	// ********** XmlContextNode implementation **********

	public TextRange getValidationTextRange() {
		return (this.xmlJarFileRef == null) ? null : this.xmlJarFileRef.getValidationTextRange();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);

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

		this.jarFile.validate(messages);
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.getJarFileName());
	}

}
