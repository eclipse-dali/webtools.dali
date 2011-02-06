/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Context JAR file
 */
public class GenericJarFile
	extends AbstractPersistenceXmlContextNode
	implements JarFile, PersistentType.Owner
{
	protected final JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot;

	protected final Vector<JavaPersistentType> javaPersistentTypes = new Vector<JavaPersistentType>();
	protected final JavaPersistentTypeContainerAdapter javaPersistentTypeContainerAdapter = new JavaPersistentTypeContainerAdapter();


	// ********** constructor/initialization **********

	public GenericJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent);
		this.jarResourcePackageFragmentRoot = jarResourcePackageFragmentRoot;
		this.initializeJavaPersistentTypes();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncJavaPersistentTypes();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getJavaPersistentTypes());
	}

	public JavaResourcePackageFragmentRoot getJarResourcePackageFragmentRoot() {
		return this.jarResourcePackageFragmentRoot;
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return null;
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


	// ********** JpaContextNode implementation **********

	@Override
	public JptResourceType getResourceType() {
		return JptCommonCorePlugin.JAR_RESOURCE_TYPE;
	}


	// ********** Java persistent types **********

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

	protected void initializeJavaPersistentTypes() {
		for (JavaResourcePersistentType jrpt : this.getJavaResourcePersistentTypes()) {
			this.javaPersistentTypes.add(this.buildJavaPersistentType(jrpt));
		}
	}

	protected void syncJavaPersistentTypes() {
		ContextContainerTools.synchronizeWithResourceModel(this.javaPersistentTypeContainerAdapter);
	}

	protected void addJavaPersistentType(JavaResourcePersistentType jrpt) {
		JavaPersistentType javaPersistentType = this.buildJavaPersistentType(jrpt);
		this.addItemToCollection(javaPersistentType, this.javaPersistentTypes, JAVA_PERSISTENT_TYPES_COLLECTION);
	}

	protected void removeJavaPersistentType(JavaPersistentType javaPersistentType ) {
		this.removeItemFromCollection(javaPersistentType, this.javaPersistentTypes, JAVA_PERSISTENT_TYPES_COLLECTION);
	}

	/**
	 * the resource JAR holds only annotated types, so we can use them all for
	 * building the context types
	 */
	protected Iterable<JavaResourcePersistentType> getJavaResourcePersistentTypes() {
		return CollectionTools.iterable(this.jarResourcePackageFragmentRoot.persistentTypes());
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType jrpt) {
		return this.getJpaFactory().buildJavaPersistentType(this, jrpt);
	}

	/**
	 * Java persistent type container adapter
	 */
	protected class JavaPersistentTypeContainerAdapter
		implements ContextContainerTools.Adapter<JavaPersistentType, JavaResourcePersistentType>
	{
		public Iterable<JavaPersistentType> getContextElements() {
			return GenericJarFile.this.getJavaPersistentTypes();
		}
		public Iterable<JavaResourcePersistentType> getResourceElements() {
			return GenericJarFile.this.getJavaResourcePersistentTypes();
		}
		public JavaResourcePersistentType getResourceElement(JavaPersistentType contextElement) {
			return contextElement.getResourcePersistentType();
		}
		public void moveContextElement(int index, JavaPersistentType element) {
			// ignore moves - we don't care about the order of the Java persistent types
		}
		public void addContextElement(int index, JavaResourcePersistentType resourceElement) {
			// ignore the index - we don't care about the order of the Java persistent types
			GenericJarFile.this.addJavaPersistentType(resourceElement);
		}
		public void removeContextElement(JavaPersistentType element) {
			GenericJarFile.this.removeJavaPersistentType(element);
		}
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
	public JarFileRef getParent() {
		return (JarFileRef) super.getParent();
	}

	protected JarFileRef getJarFileRef() {
		return this.getParent();
	}

	@Override
	public IResource getResource() {
		return this.jarResourcePackageFragmentRoot.getFile();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO validate 'javaPersistentTypes'
	}

	public boolean isIn(org.eclipse.core.resources.IFolder folder) {
		IResource member = folder.findMember(this.jarResourcePackageFragmentRoot.getFile().getName());
		IFile file = this.jarResourcePackageFragmentRoot.getFile();
		return member != null && file != null && member.equals(file);		
	}

	public TextRange getValidationTextRange() {
		return this.getJarFileRef().getValidationTextRange();
	}
}
