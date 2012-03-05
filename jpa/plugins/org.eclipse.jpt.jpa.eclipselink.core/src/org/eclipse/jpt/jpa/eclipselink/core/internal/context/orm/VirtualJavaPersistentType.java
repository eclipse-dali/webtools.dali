/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.BodySourceWriter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ChainIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class VirtualJavaPersistentType
	extends AbstractJavaJpaContextNode
	implements JavaPersistentType2_0
{

	private final XmlTypeMapping xmlTypeMapping;

	protected final JavaTypeMapping mapping;
	protected PersistentType superPersistentType;

	public VirtualJavaPersistentType(EclipseLinkOrmPersistentType parent, XmlTypeMapping xmlTypeMapping) {
		super(parent);
		this.xmlTypeMapping = xmlTypeMapping;
		this.mapping = new JavaNullTypeMapping(this);
	}

	@Override
	public EclipseLinkOrmPersistentType getParent() {
		return (EclipseLinkOrmPersistentType) super.getParent();
	}

	protected EclipseLinkEntityMappings getEntityMappings() {
		return (EclipseLinkEntityMappings) getParent().getMappingFileRoot();
	}


	// ********** synchronize/update **********
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.setSuperPersistentType(this.buildSuperPersistentType());
	}


	// ********** name **********

	//The parent OrmPersistentType builds its name from the specified class and package.
	//In SpecifiedOrmPersistentType.updateJavaPersistentType(), it compares the names and 
	//rebuilds if the name has changed. We don't need to rebuild the virtual java persistent 
	//type based on a name change, it will get rebuilt if the dynamic state changes.
	public String getName() {
		return this.getParent().getName();
	}

	public String getSimpleName() {
		return this.getParent().getSimpleName();
	}


	// ********** access **********

	public AccessType getSpecifiedAccess() {
		return null;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		throw new UnsupportedOperationException();
	}

	public AccessType getDefaultAccess() {
		return null;
	}

	public AccessType getAccess() {
		return null;
	}


	// ********** mapping **********

	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String key) {
		throw new UnsupportedOperationException();
	}

	public boolean isMapped() {
		return false;
	}


	// ********** super persistent type **********

	public PersistentType getSuperPersistentType() {
		return this.superPersistentType;
	}

	protected void setSuperPersistentType(PersistentType superPersistentType) {
		PersistentType old = this.superPersistentType;
		this.superPersistentType = superPersistentType;
		this.firePropertyChanged(SUPER_PERSISTENT_TYPE_PROPERTY, old, superPersistentType);
	}

	protected PersistentType buildSuperPersistentType() {
		HashSet<JavaResourceType> visited = new HashSet<JavaResourceType>();
		PersistentType spt = this.resolveSuperPersistentType(this.xmlTypeMapping.getParentClass(), visited);
		if (spt == null) {
			return null;
		}
		if (CollectionTools.contains(spt.getInheritanceHierarchy(), this)) {
			return null;  // short-circuit in this case, we have circular inheritance
		}
		return spt.isMapped() ? spt : spt.getSuperPersistentType();
	}

	/**
	 * The JPA spec allows non-persistent types in a persistent type's
	 * inheritance hierarchy. We check for a persistent type with the
	 * specified name in the persistence unit (Use the EntityMapping
	 * API for this because it will append the package name if unqualified).
	 * If it is not found we use java resource type and look for <em>its</em> super type.
	 * <p>
	 * The <code>visited</code> collection is used to detect a cycle in the
	 * <em>resource</em> type inheritance hierarchy and prevent the resulting
	 * stack overflow. Any cycles in the <em>context</em> type inheritance
	 * hierarchy are handled in {@link #buildSuperPersistentType()}.
	 */
	protected PersistentType resolveSuperPersistentType(String typeName, Collection<JavaResourceType> visited) {
		if (StringTools.stringIsEmpty(typeName)) {
			return null;
		}
		PersistentType spt = this.resolvePersistentType(typeName);
		if (spt != null) {
			return spt;
		}
		JavaResourceType resourceType = this.resolveJavaResourceType(typeName);
		visited.add(resourceType);
		return (resourceType == null) ? null : this.resolveSuperPersistentType(resourceType.getSuperclassQualifiedName(), visited);  // recurse
	}

	protected PersistentType resolvePersistentType(String typeName) {
		return getEntityMappings().resolvePersistentType(typeName);
	}

	protected JavaResourceType resolveJavaResourceType(String typeName) {
		return (JavaResourceType) this.getEntityMappings().resolveJavaResourceType(typeName, JavaResourceAnnotatedElement.Kind.TYPE);
	}


	// ********** attributes **********
	//The VirtualJavaPersistentAttributes are built by the OrmEclipseLinkPersistentAttribute, no attributes here
	
	public ListIterable<JavaPersistentAttribute> getAttributes() {
		return EmptyListIterable.instance();
	}

	public JavaPersistentAttribute getAttributeNamed(String attributeName) {
		return null;
	}

	public boolean hasAnyAnnotatedAttributes() {
		return false;
	}

	public JavaPersistentAttribute getAttributeFor(JavaResourceAttribute javaResourceAttribute) {
		return null;
	}

	public int getAttributesSize() {
		return 0;
	}

	public Iterable<String> getAttributeNames() {
		return EmptyIterable.instance();
	}

	public Iterable<ReadOnlyPersistentAttribute> getAllAttributes() {
		return EmptyIterable.instance();
	}

	public Iterable<String> getAllAttributeNames() {
		return EmptyIterable.instance();
	}

	public ReadOnlyPersistentAttribute resolveAttribute(String attributeName) {
		return null;
	}


	// ********** inheritance **********

	public Iterable<PersistentType> getInheritanceHierarchy() {
		return this.getInheritanceHierarchyOf(this);
	}

	public Iterable<PersistentType> getAncestors() {
		return this.getInheritanceHierarchyOf(this.superPersistentType);
	}

	protected Iterable<PersistentType> getInheritanceHierarchyOf(PersistentType start) {
		// using a chain iterable to traverse up the inheritance tree
		return new ChainIterable<PersistentType>(start) {
			@Override
			protected PersistentType nextLink(PersistentType persistentType) {
				return persistentType.getSuperPersistentType();
			}
		};
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent type"); //$NON-NLS-1$
	}

	public Class<? extends JpaStructureNode> getType() {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent type"); //$NON-NLS-1$
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent type"); //$NON-NLS-1$
	}

	public TextRange getSelectionTextRange() {
		throw new UnsupportedOperationException("There is no resource for a virtual java persistent type"); //$NON-NLS-1$
	}


	// ********** validation **********

	public void validate(List<IMessage> messages, IReporter reporter) {
		throw new UnsupportedOperationException();
	}

	public TextRange getValidationTextRange() {
		throw new UnsupportedOperationException();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}


	// ********** misc **********

	public JavaResourceType getJavaResourceType() {
		return null;
	}

	public AccessType getOwnerOverrideAccess() {
		throw new UnsupportedOperationException();
	}

	public AccessType getOwnerDefaultAccess() {
		throw new UnsupportedOperationException();
	}

	public boolean isFor(String typeName) {
		throw new UnsupportedOperationException();
	}

	public boolean isIn(IPackageFragment packageFragment) {
		throw new UnsupportedOperationException();
	}

	public PersistentType getOverriddenPersistentType() {
		throw new UnsupportedOperationException();
	}

	public String getDeclaringTypeName() {
		String className = this.xmlTypeMapping.getClassName();
		int index = className == null ? -1 : className.lastIndexOf('$');
		if (index == -1) {
			return null;
		}
		return className.substring(0, index).replace('$', '.');
	}

	public boolean isManaged() {
		throw new UnsupportedOperationException();
	}

	public IFile getMetamodelFile() {
		throw new UnsupportedOperationException();
	}

	public void synchronizeMetamodel(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		throw new UnsupportedOperationException();
	}

	public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}

	public void dispose() {
		//nothing to dispose
	}
}
