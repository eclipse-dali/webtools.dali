/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
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

	protected final ContextCollectionContainer<JavaPersistentType, JavaResourceType> javaPersistentTypeContainer;


	// ********** constructor/initialization **********

	public GenericJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent);
		this.jarResourcePackageFragmentRoot = jarResourcePackageFragmentRoot;
		this.javaPersistentTypeContainer = this.buildJavaPersistentTypeContainer();
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
		this.updateJavaPersistentTypes();
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

	public Iterable<JavaPersistentType> getJavaPersistentTypes() {
		return this.javaPersistentTypeContainer.getContextElements();
	}

	public int getJavaPersistentTypesSize() {
		return this.javaPersistentTypeContainer.getContextElementsSize();
	}

	protected void syncJavaPersistentTypes() {
		this.javaPersistentTypeContainer.synchronizeWithResourceModel();
	}

	protected void updateJavaPersistentTypes() {
		this.javaPersistentTypeContainer.update();
	}

	protected void addJavaPersistentType(JavaResourceType jrt) {
		this.javaPersistentTypeContainer.addContextElement(getJavaPersistentTypesSize(), jrt);
	}

	protected void removeJavaPersistentType(JavaPersistentType javaPersistentType ) {
		this.javaPersistentTypeContainer.removeContextElement(javaPersistentType);
	}

	//only accept types, enums aren't valid for JPA
	protected Iterable<JavaResourceType> getJavaResourceTypes() {
		return new SubIterableWrapper<JavaResourceAbstractType, JavaResourceType>(
			new FilteringIterable<JavaResourceAbstractType>(this.getJavaResourceAbstractTypes()) {
				@Override
				protected boolean accept(JavaResourceAbstractType o) {
					return o.getKind() == Kind.TYPE;
				}
			});
	}

	/**
	 * the resource JAR holds only annotated types, so we can use them all for
	 * building the context types
	 */
	protected Iterable<JavaResourceAbstractType> getJavaResourceAbstractTypes() {
		return this.jarResourcePackageFragmentRoot.getTypes();
	}

	protected JavaPersistentType buildJavaPersistentType(JavaResourceType jrt) {
		return this.getJpaFactory().buildJavaPersistentType(this, jrt);
	}

	protected ContextCollectionContainer<JavaPersistentType, JavaResourceType> buildJavaPersistentTypeContainer() {
		return new JavaPersistentTypeContainer();
	}

	/**
	 * Java persistent type container
	 */
	protected class JavaPersistentTypeContainer
		extends ContextCollectionContainer<JavaPersistentType, JavaResourceType>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return JAVA_PERSISTENT_TYPES_COLLECTION;
		}
		@Override
		protected JavaPersistentType buildContextElement(JavaResourceType resourceElement) {
			return GenericJarFile.this.buildJavaPersistentType(resourceElement);
		}
		@Override
		protected Iterable<JavaResourceType> getResourceElements() {
			return GenericJarFile.this.getJavaResourceTypes();
		}
		@Override
		protected JavaResourceType getResourceElement(JavaPersistentType contextElement) {
			return contextElement.getJavaResourceType();
		}
	}


	// ********** PersistentTypeContainer implementation **********

	public Iterable<JavaPersistentType> getPersistentTypes() {
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
