/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
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

	public GenericJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent);
		this.jarResourcePackageFragmentRoot = jarResourcePackageFragmentRoot;
		CollectionTools.addAll(this.javaPersistentTypes, this.buildJavaPersistentTypes());
	}

	protected Iterator<JavaPersistentType> buildJavaPersistentTypes() {
		return new TransformationIterator<JavaResourcePersistentType, JavaPersistentType>(this.javaResourcePersistentTypes()) {
			@Override
			protected JavaPersistentType transform(JavaResourcePersistentType jrpt) {
				return GenericJarFile.this.buildJavaPersistentType(jrpt);
			}
		};
	}

	/**
	 * the resource JAR holds only annotated types, so we can use them all for
	 * building the context types
	 */
	protected Iterator<JavaResourcePersistentType> javaResourcePersistentTypes() {
		return this.jarResourcePackageFragmentRoot.persistentTypes();
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType jrpt) {
		return this.getJpaFactory().buildJavaPersistentType(this, jrpt);
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return null;
	}
	
	@Override
	public JpaResourceType getResourceType() {
		return JptCorePlugin.JAR_RESOURCE_TYPE;
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

	public JavaPersistentType getPersistentType(String typeName) {
		for (JavaPersistentType pt : this.getJavaPersistentTypes()) {
			if (pt.getName().equals(typeName)) {
				return pt;
			}
		}
		return null;
	}

	public Iterator<JavaPersistentType> javaPersistentTypes() {
		return this.getJavaPersistentTypes().iterator();
	}

	protected Iterable<JavaPersistentType> getJavaPersistentTypes() {
		return new LiveCloneIterable<JavaPersistentType>(this.javaPersistentTypes);
	}

	public int javaPersistentTypesSize() {
		return this.javaPersistentTypes.size();
	}

	protected JavaPersistentType addJavaPersistentType(JavaResourcePersistentType jrpt) {
		JavaPersistentType javaPersistentType = this.buildJavaPersistentType(jrpt);
		this.addItemToCollection(javaPersistentType, this.javaPersistentTypes, JAVA_PERSISTENT_TYPES_COLLECTION);
		return javaPersistentType;
	}

	protected void removeJavaPersistentType(JavaPersistentType javaPersistentType ) {
		this.removeItemFromCollection(javaPersistentType, this.javaPersistentTypes, JAVA_PERSISTENT_TYPES_COLLECTION);
	}


	// ********** PersistentTypeContainer implementation **********

	public Iterable<? extends PersistentType> getPersistentTypes() {
		return this.getJavaPersistentTypes();
	}


	// ********** PersistentType.Owner implementation **********

	public AccessType getDefaultPersistentTypeAccess() {
		return this.getPersistenceUnit().getDefaultAccess();
	}

	public AccessType getOverridePersistentTypeAccess() {
		// no access type at this level overrides any local access type specification
		return null;
	}
	
	
	// ********** JpaNode implementation **********
	
	@Override
	public IResource getResource() {
		return this.jarResourcePackageFragmentRoot.getFile();
	}


	// ********** updating **********

	public void update(JavaResourcePackageFragmentRoot jrpfr) {
		this.jarResourcePackageFragmentRoot = jrpfr;
		this.updateJavaPersistentTypes();
	}

	protected void updateJavaPersistentTypes() {
		HashBag<JavaPersistentType> contextTypesToRemove = CollectionTools.bag(this.javaPersistentTypes(), this.javaPersistentTypes.size());
		ArrayList<JavaPersistentType> contextTypesToUpdate = new ArrayList<JavaPersistentType>(this.javaPersistentTypes.size());

		for (Iterator<JavaResourcePersistentType> resourceTypes = this.javaResourcePersistentTypes(); resourceTypes.hasNext(); ) {
			JavaResourcePersistentType resourceType = resourceTypes.next();
			boolean match = false;
			for (Iterator<JavaPersistentType> contextTypes = contextTypesToRemove.iterator(); contextTypes.hasNext(); ) {
				JavaPersistentType contextType = contextTypes.next();
				if (contextType.getResourcePersistentType() == resourceType) {
					contextTypes.remove();
					contextTypesToUpdate.add(contextType);
					match = true;
					break;
				}
			}
			if ( ! match) {
				this.addJavaPersistentType(resourceType);
			}
		}
		for (JavaPersistentType contextType : contextTypesToRemove) {
			this.removeJavaPersistentType(contextType);
		}
		// handle adding and removing java persistent types first, update the
		// remaining java persistent types last; this reduces the churn during "update"
		for (JavaPersistentType contextType : contextTypesToUpdate) {
			contextType.update();
		}
	}


	// ********** validation **********

	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
		// TODO validate 'javaPersistentTypes'
	}
	
}
