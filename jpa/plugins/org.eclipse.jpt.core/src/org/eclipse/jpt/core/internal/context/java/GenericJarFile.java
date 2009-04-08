/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CloneIterable;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Context JAR file
 */
public class GenericJarFile
	extends AbstractJpaContextNode
	implements JarFile, PersistentType.Owner
{
	protected JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot;
	protected final Vector<JavaPersistentType> javaPersistentTypes = new Vector<JavaPersistentType>();


	// ********** constructor/initialization **********

	public GenericJarFile(JpaNode parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent);
		this.jarResourcePackageFragmentRoot = jarResourcePackageFragmentRoot;
		CollectionTools.addAll(this.javaPersistentTypes, this.buildJavaPersistentTypes());
	}

	protected Iterator<JavaPersistentType> buildJavaPersistentTypes() {
		return new TransformationIterator<JavaResourcePersistentType, JavaPersistentType>(this.jarResourcePackageFragmentRoot.persistableTypes()) {
			@Override
			protected JavaPersistentType transform(JavaResourcePersistentType jrpt) {
				return GenericJarFile.this.buildJavaPersistentType(jrpt);
			}
		};
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType jrpt) {
		return this.getJpaFactory().buildJavaPersistentType(this, jrpt);
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
		for (JavaPersistentType pt : this.getJavaPersistentTypes()) {
			if (pt.getName().equals(typeName)) {
				return pt;
			}
		}
		return null;
	}

	protected Iterable<JavaPersistentType> getJavaPersistentTypes() {
		return new CloneIterable<JavaPersistentType>(this.javaPersistentTypes);
	}


	// ********** PersistentType.Owner implementation **********

	public AccessType getDefaultPersistentTypeAccess() {
		return this.getPersistenceUnit().getDefaultAccess();
	}

	public AccessType getOverridePersistentTypeAccess() {
		// no access type at this level overrides any local access type specification
		return null;
	}


	// ********** updating **********

	public void update(JavaResourcePackageFragmentRoot jrpfr) {
		this.jarResourcePackageFragmentRoot = jrpfr;
	}


	// ********** validation **********

	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
	}

}
