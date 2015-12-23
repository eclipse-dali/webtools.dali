/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaPersistentTypeDefinition;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextModel;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Context JAR file
 */
public class GenericJarFile
	extends AbstractPersistenceXmlContextModel<JarFileRef>
	implements JarFile, JavaPersistentType.Parent
{
	protected final JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot;

	protected final ContextCollectionContainer<JavaManagedType, JavaResourceType> javaManagedTypeContainer;


	// ********** constructor/initialization **********

	public GenericJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		super(parent);
		this.jarResourcePackageFragmentRoot = jarResourcePackageFragmentRoot;
		this.javaManagedTypeContainer = this.buildJavaManagedTypeContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncJavaManagedTypes();
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getJavaManagedTypes(), monitor);
	}

	public JavaResourcePackageFragmentRoot getJarResourcePackageFragmentRoot() {
		return this.jarResourcePackageFragmentRoot;
	}


	// ********** JpaContextNode implementation **********

	@Override
	public JptResourceType getResourceType() {
		return ContentTypeTools.getResourceType(JavaResourcePackageFragmentRoot.JAR_CONTENT_TYPE);
	}


	// ********** Java managed types **********

	public JavaManagedType getManagedType(String typeName) {
		for (JavaManagedType mt : this.getJavaManagedTypes()) {
			if (mt.getName().equals(typeName)) {
				return mt;
			}
		}
		return null;
	}

	public Iterable<JavaManagedType> getJavaManagedTypes() {
		return this.javaManagedTypeContainer;
	}

	public int getJavaManagedTypesSize() {
		return this.javaManagedTypeContainer.size();
	}

	protected void syncJavaManagedTypes() {
		this.javaManagedTypeContainer.synchronizeWithResourceModel();
	}

	protected void addJavaManagedType(JavaResourceType jrt) {
		this.javaManagedTypeContainer.addContextElement(getJavaManagedTypesSize(), jrt);
	}

	protected void removeJavaManagedType(JavaManagedType javaManagedType) {
		this.javaManagedTypeContainer.remove(javaManagedType);
	}

	//only accept types, enums aren't valid for JPA
	protected Iterable<JavaResourceType> getJavaResourceTypes() {
		return IterableTools.downCast(
				IterableTools.filter(
					this.getJavaResourceAbstractTypes(),
					new JavaResourceAnnotatedElement.AstNodeTypeEquals(AstNodeType.TYPE)
				)
			);
	}

	/**
	 * the resource JAR holds only annotated types, so we can use them all for
	 * building the context types
	 */
	protected Iterable<JavaResourceAbstractType> getJavaResourceAbstractTypes() {
		return this.jarResourcePackageFragmentRoot.getTypes();
	}

	protected JavaManagedType buildJavaManagedType(JavaResourceType jrt, JavaManagedTypeDefinition managedTypeDefinition) {
		return managedTypeDefinition.buildContextManagedType(this, jrt, this.getJpaFactory());
	}

	protected Iterable<JavaManagedTypeDefinition> getJavaManagedTypeDefinitions() {
		return this.getJpaPlatform().getJavaManagedTypeDefinitions();
	}

	protected JavaManagedTypeDefinition getJavaManagedTypeDefinition(JavaResourceType jrt) {
		for (JavaManagedTypeDefinition managedTypeDefinition : this.getJavaManagedTypeDefinitions()) {
			if (jrt.isAnnotatedWithAnyOf(managedTypeDefinition.getAnnotationNames(this.getJpaProject()))) {
				return managedTypeDefinition;
			}
		}
		return JavaPersistentTypeDefinition.instance();
	}

	protected JavaManagedType buildJavaManagedType(JavaResourceType jrt) {
		return getJavaManagedTypeDefinition(jrt).buildContextManagedType(this, jrt, getJpaFactory());
	}

	protected ContextCollectionContainer<JavaManagedType, JavaResourceType> buildJavaManagedTypeContainer() {
		return this.buildSpecifiedContextCollectionContainer(JAVA_MANAGED_TYPES_COLLECTION, new JavaManagedTypeContainerAdapter());
	}

	/**
	 * Java managed type container adapter
	 */
	public class JavaManagedTypeContainerAdapter
		extends AbstractContainerAdapter<JavaManagedType, JavaResourceType>
	{
		public JavaManagedType buildContextElement(JavaResourceType resourceElement) {
			return GenericJarFile.this.buildJavaManagedType(resourceElement);
		}
		public Iterable<JavaResourceType> getResourceElements() {
			return GenericJarFile.this.getJavaResourceTypes();
		}
		public JavaResourceType extractResourceElement(JavaManagedType contextElement) {
			return contextElement.getJavaResourceType();
		}
	}


	// ********** ManagedTypeContainer implementation **********

	public Iterable<JavaManagedType> getManagedTypes() {
		return this.getJavaManagedTypes();
	}


	// ********** PersistentTypeContainer implementation **********

	public JavaPersistentType getPersistentType(String typeName) {
		JavaManagedType managedType = this.getManagedType(typeName);
		return (managedType.getManagedTypeType() == PersistentType.class) ? (JavaPersistentType) managedType : null;
	}

	public Iterable<JavaPersistentType> getPersistentTypes() {
		return IterableTools.downCast(IterableTools.filter(this.getManagedTypes(), TYPE_IS_PERSISTENT_TYPE));
	}

	protected static final Predicate<JavaManagedType> TYPE_IS_PERSISTENT_TYPE = new TypeIsPersistentType();

	public static class TypeIsPersistentType
		extends PredicateAdapter<JavaManagedType>
	{
		@Override
		public boolean evaluate(JavaManagedType mt) {
			return mt.getManagedTypeType() == PersistentType.class;
		}
	}


	// ********** JavaPersistentType.Parent implementation **********

	public AccessType getDefaultPersistentTypeAccess() {
		return this.getPersistenceUnit().getDefaultAccess();
	}

	public AccessType getOverridePersistentTypeAccess() {
		// no access type at this level overrides any local access type specification
		return null;
	}

	public void attributeChanged(JavaSpecifiedPersistentAttribute attribute) {
		// NOP
	}


	// ********** JpaNode implementation **********

	protected JarFileRef getJarFileRef() {
		return this.parent;
	}

	@Override
	public IResource getResource() {
		return this.jarResourcePackageFragmentRoot.getFile();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO validate 'javaManagedTypes'
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
