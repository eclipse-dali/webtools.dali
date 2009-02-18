/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.jar;

import java.util.List;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.jar.JarFile;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJarFile
	extends AbstractJpaContextNode
	implements JarFile
{
	protected JarResourcePackageFragmentRoot jarResourcePackageFragmentRoot;


	// ********** constructor **********

	public GenericJarFile(JpaNode parent, JarResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent);
		this.jarResourcePackageFragmentRoot = jarResourcePackageFragmentRoot;
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return null;
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.JAR_CONTENT_TYPE;
	}
	
	public TextRange getSelectionTextRange() {
		return null;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return null;
	}

	public void dispose() {
		// nothing yet
	}


	// ********** JarFile implementation **********

	public PersistentType getPersistentType(String typeName) {
		return null;
	}


	// ********** updating **********

	public void update(JarResourcePackageFragmentRoot jrpfr) {
		this.jarResourcePackageFragmentRoot = jrpfr;
	}


	// ********** validation **********

	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
	}

}
