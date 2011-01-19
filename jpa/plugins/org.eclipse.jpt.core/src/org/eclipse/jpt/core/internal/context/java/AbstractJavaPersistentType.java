/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ClassName;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.ChainIterable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java persistent type:<ul>
 * <li>name
 * <li>access
 * <li>mapping
 * <li>attributes
 * <li>super persistent type
 * </ul>
 */
public abstract class AbstractJavaPersistentType
	extends AbstractJavaJpaContextNode
	implements JavaPersistentType
{
	protected final JavaResourcePersistentType resourcePersistentType;

	protected String name;

	protected PersistentType superPersistentType;

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;  // never null

	protected JavaTypeMapping mapping;  // never null

	protected final Vector<JavaPersistentAttribute> attributes = new Vector<JavaPersistentAttribute>();
	protected final AttributeContainerAdapter attributeContainerAdapter = new AttributeContainerAdapter();


	protected AbstractJavaPersistentType(PersistentType.Owner parent, JavaResourcePersistentType resourcePersistentType) {
		super(parent);
		this.resourcePersistentType = resourcePersistentType;
		this.name = resourcePersistentType.getQualifiedName();
		this.specifiedAccess = this.buildSpecifiedAccess();

		// keep this non-null
		this.defaultAccess = AccessType.FIELD;

		this.mapping = this.buildMapping();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName(this.resourcePersistentType.getQualifiedName());
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.syncMapping();
		this.synchronizeNodesWithResourceModel(this.getAttributes());
	}

	@Override
	public void update() {
		super.update();
		this.setSuperPersistentType(this.buildSuperPersistentType());
		this.setDefaultAccess(this.buildDefaultAccess());
		this.mapping.update();
		this.updateAttributes();
		this.registerRootStructureNode();
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public String getSimpleName(){
		return ClassName.getSimpleName(this.name);
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
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
		HashSet<JavaResourcePersistentType> visited = new HashSet<JavaResourcePersistentType>();
		visited.add(this.resourcePersistentType);
		PersistentType spt = this.resolveSuperPersistentType(this.resourcePersistentType.getSuperclassQualifiedName(), visited);
		if (spt == null) {
			return null;
		}
		if (CollectionTools.contains(spt.inheritanceHierarchy(), this)) {
			return null;  // short-circuit in this case, we have circular inheritance
		}
		return spt.isMapped() ? spt : spt.getSuperPersistentType();
	}

	/**
	 * The JPA spec allows non-persistent types in a persistent type's
	 * inheritance hierarchy. We check for a persistent type with the
	 * specified name in the persistence unit. If it is not found we use
	 * resource persistent type and look for <em>its</em> super type.
	 * <p>
	 * The <code>visited</code> collection is used to detect a cycle in the
	 * <em>resource</em> type inheritance hierarchy and prevent the resulting
	 * stack overflow. Any cycles in the <em>context</em> type inheritance
	 * hierarchy are handled in {@link #buildSuperPersistentType()}.
	 */
	protected PersistentType resolveSuperPersistentType(String typeName, Collection<JavaResourcePersistentType> visited) {
		if (typeName == null) {
			return null;
		}
		JavaResourcePersistentType resourceType = this.getJpaProject().getJavaResourcePersistentType(typeName);
		if ((resourceType == null) || visited.contains(resourceType)) {
			return null;
		}
		visited.add(resourceType);
		PersistentType spt = this.resolvePersistentType(typeName);
		return (spt != null) ? spt : this.resolveSuperPersistentType(resourceType.getSuperclassQualifiedName(), visited);  // recurse
	}

	protected PersistentType resolvePersistentType(String typeName) {
		return this.getPersistenceUnit().getPersistentType(typeName);
	}


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}
	
	/**
	 * Build an access type based on annotations from the resource model.
	 * (This is JPA platform-dependent.)
	 */
	protected abstract AccessType buildSpecifiedAccess();

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
	}

	/**
	 * Check the access "specified" by the Java resource model:<ul>
	 * <li>Check Java annotations first
	 * <li>If <code>null</code>, check XML mapping specified access
	 * <li>If still <code>null</code>, check {@link #superPersistentType} access
	 * <li>If still <code>null</code>, check <code>entity-mappings</code>
	 *     specified access setting if the corresponding <code>persistent-type</code>
	 *     is listed in a mapping (<code>orm.xml</code>) file
	 * <li>If still <code>null</code>, check the <code>persistence-unit</code>
	 *     default Access
	 * <li>Default to {@link AccessType#FIELD FIELD} if all else fails.
	 * </ul>
	 */
	protected AccessType buildDefaultAccess() {
		AccessType accessType = AccessType.fromJavaResourceModel(JPTTools.buildAccess(this.resourcePersistentType));
		if (accessType != null) {
			return accessType;
		}
		accessType = this.getOwnerOverrideAccess();
		if (accessType != null) {
			return accessType;
		}

		if (this.superPersistentType != null) {
			accessType = this.superPersistentType.getAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		accessType = this.getOwnerDefaultAccess();
		if (accessType != null) {
			return accessType;
		}

		// last ditch attempt to allow the user to annotate *something*
		return AccessType.FIELD;
	}


	// ********** mapping **********

	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String key) {
		if (this.valuesAreDifferent(key, this.getMappingKey())) {
			this.setMapping(this.buildMapping(key));
		}
	}

	protected JavaTypeMapping buildMapping(String key) {
		for (JavaTypeMappingDefinition definition : this.getMappingDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), key)) {
				Annotation annotation = this.resourcePersistentType.setPrimaryAnnotation(definition.getAnnotationName(), definition.getSupportingAnnotationNames());
				return definition.buildMapping(this, annotation, this.getJpaFactory());
			}
		}
		this.resourcePersistentType.setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		return this.buildNullMapping();
	}

	/**
	 * Clients do not set the mapping directly.
	 * @see #setMappingKey(String)
	 */
	protected void setMapping(JavaTypeMapping mapping) {
		JavaTypeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(MAPPING_PROPERTY, old, mapping);
	}

	protected JavaTypeMapping buildMapping() {
		for (JavaTypeMappingDefinition definition : this.getMappingDefinitions()) {
			Annotation annotation = this.resourcePersistentType.getAnnotation(definition.getAnnotationName());
			if (annotation != null) {
				return definition.buildMapping(this, annotation, this.getJpaFactory());
			}
		}
		return this.buildNullMapping();
	}

	protected void syncMapping() {
		JavaTypeMappingDefinition definition = null;
		Annotation annotation = null;
		for (Iterator<JavaTypeMappingDefinition> stream = this.mappingDefinitions(); stream.hasNext(); ) {
			definition = stream.next();
			annotation = this.resourcePersistentType.getAnnotation(definition.getAnnotationName());
			if (annotation != null) {
				break;
			}
		}
		// 'annotation' can still be null when we get here
		if (this.mapping.getMappingAnnotation() == annotation) {
			this.mapping.synchronizeWithResourceModel();
		} else {
			this.setMapping(this.buildMapping(annotation, definition));
		}
	}

	protected JavaTypeMapping buildMapping(Annotation annotation, JavaTypeMappingDefinition definition) {
		return (annotation != null) ?
				definition.buildMapping(this, annotation, this.getJpaFactory()) :
				this.buildNullMapping();
	}

	protected Iterator<JavaTypeMappingDefinition> mappingDefinitions() {
		return this.getMappingDefinitions().iterator();
	}

	protected Iterable<JavaTypeMappingDefinition> getMappingDefinitions() {
		return this.getJpaPlatform().getJavaTypeMappingDefinitions();
	}

	protected JavaTypeMapping buildNullMapping() {
		return this.getJpaFactory().buildJavaNullTypeMapping(this);
	}

	public boolean isMapped() {
		return this.mapping.isMapped();
	}


	// ********** attributes **********

	public ListIterator<JavaPersistentAttribute> attributes() {
		return this.getAttributes().iterator();
	}

	protected ListIterable<JavaPersistentAttribute> getAttributes() {
		return new LiveCloneListIterable<JavaPersistentAttribute>(this.attributes);
	}

	public int attributesSize() {
		return this.attributes.size();
	}

	public Iterator<String> attributeNames() {
		return this.getAttributeNames().iterator();
	}

	protected Iterable<String> getAttributeNames() {
		return this.convertToNames(this.getAttributes());
	}

	public JavaPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	public JavaPersistentAttribute getAttributeFor(JavaResourcePersistentAttribute javaResourceAttribute) {
		for (JavaPersistentAttribute javaAttribute : this.getAttributes()) {
			if (javaAttribute.getResourcePersistentAttribute() == javaResourceAttribute) {
				return javaAttribute;
			}
		}
		return null;
	}

	public Iterator<ReadOnlyPersistentAttribute> allAttributes() {
		return this.getAllAttributes().iterator();
	}

	protected Iterable<ReadOnlyPersistentAttribute> getAllAttributes() {
		return new CompositeIterable<ReadOnlyPersistentAttribute>(
				new TransformationIterable<PersistentType, Iterable<ReadOnlyPersistentAttribute>>(this.getInheritanceHierarchy()) {
					@Override
					protected Iterable<ReadOnlyPersistentAttribute> transform(PersistentType pt) {
						return new SnapshotCloneIterable<ReadOnlyPersistentAttribute>(pt.attributes());
					}
				}
			);
	}

	public Iterator<String> allAttributeNames() {
		return this.getAllAttributeNames().iterator();
	}

	protected Iterable<String> getAllAttributeNames() {
		return this.convertToNames(this.getAllAttributes());
	}

	protected Iterable<JavaPersistentAttribute> getAttributesNamed(final String attributeName) {
		return new FilteringIterable<JavaPersistentAttribute>(this.getAttributes()) {
			@Override
			protected boolean accept(JavaPersistentAttribute attribute) {
				return Tools.valuesAreEqual(attributeName, attribute.getName());
			}
		};
	}

	public ReadOnlyPersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		if (stream.hasNext()) {
			JavaPersistentAttribute attribute = stream.next();
			// return null if we have more than one
			return stream.hasNext() ? null : attribute;
		}
		// recurse
		return (this.superPersistentType == null) ? null : this.superPersistentType.resolveAttribute(attributeName);
	}

	protected Iterable<String> convertToNames(Iterable<? extends ReadOnlyPersistentAttribute> attrs) {
		return new TransformationIterable<ReadOnlyPersistentAttribute, String>(attrs) {
			@Override
			protected String transform(ReadOnlyPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	protected Iterator<JavaResourcePersistentAttribute> resourceAttributes() {
		return (this.getAccess() == AccessType.PROPERTY) ?
				this.resourcePersistentType.persistableProperties() :
				this.resourcePersistentType.persistableFields();
	}

	protected Iterable<JavaResourcePersistentAttribute> getResourceAttributes() {
		return CollectionTools.iterable(this.resourceAttributes());
	}

	protected JavaPersistentAttribute buildAttribute(JavaResourcePersistentAttribute resourceAttribute) {
		return this.getJpaFactory().buildJavaPersistentAttribute(this, resourceAttribute);
	}

	public boolean hasAnyAnnotatedAttributes() {
		return this.resourcePersistentType.hasAnyAnnotatedAttributes();
	}

	/**
	 * The attributes are synchronized during the <em>update</em> because
	 * the list of resource attributes is determined by the access type
	 * which can be controlled in a number of different places....
	 */
	protected void updateAttributes() {
		ContextContainerTools.update(this.attributeContainerAdapter);
	}

	protected void moveAttribute(int index, JavaPersistentAttribute attribute) {
		this.moveItemInList(index, attribute, this.attributes, ATTRIBUTES_LIST);
	}

	protected void addAttribute(int index, JavaResourcePersistentAttribute resourceAttribute) {
		this.addItemToList(index, this.buildAttribute(resourceAttribute), this.attributes, ATTRIBUTES_LIST);
	}

	protected void removeAttribute(JavaPersistentAttribute attribute) {
		this.removeItemFromList(attribute, this.attributes, ATTRIBUTES_LIST);
	}

	/**
	 * attribute container adapter
	 */
	protected class AttributeContainerAdapter
		implements ContextContainerTools.Adapter<JavaPersistentAttribute, JavaResourcePersistentAttribute>
	{
		public Iterable<JavaPersistentAttribute> getContextElements() {
			return AbstractJavaPersistentType.this.getAttributes();
		}
		public Iterable<JavaResourcePersistentAttribute> getResourceElements() {
			return AbstractJavaPersistentType.this.getResourceAttributes();
		}
		public JavaResourcePersistentAttribute getResourceElement(JavaPersistentAttribute contextElement) {
			return contextElement.getResourcePersistentAttribute();
		}
		public void moveContextElement(int index, JavaPersistentAttribute element) {
			AbstractJavaPersistentType.this.moveAttribute(index, element);
		}
		public void addContextElement(int index, JavaResourcePersistentAttribute resourceElement) {
			AbstractJavaPersistentType.this.addAttribute(index, resourceElement);
		}
		public void removeContextElement(JavaPersistentAttribute element) {
			AbstractJavaPersistentType.this.removeAttribute(element);
		}
	}


	// ********** inheritance **********

	public Iterator<PersistentType> inheritanceHierarchy() {
		return this.getInheritanceHierarchy().iterator();
	}

	public Iterable<PersistentType> getInheritanceHierarchy() {
		return this.getInheritanceHierarchyOf(this);
	}

	public Iterator<PersistentType> ancestors() {
		return this.getAncestors().iterator();
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

	public String getId() {
		return JavaStructureNodes.PERSISTENT_TYPE_ID;
	}

	// it would be nice if the we passed in an astRoot here, but then we
	// would need to pass it to the XML structure nodes too...
	public JpaStructureNode getStructureNode(int offset) {
		CompilationUnit astRoot = this.buildASTRoot();

		if (this.contains(offset, astRoot)) {
			for (JavaPersistentAttribute persistentAttribute : this.getAttributes()) {
				if (persistentAttribute.contains(offset, astRoot)) {
					return persistentAttribute;
				}
			}
			return this;
		}
		return null;
	}

	protected boolean contains(int offset, CompilationUnit astRoot) {
		TextRange fullTextRange = this.getFullTextRange(astRoot);
		// 'fullTextRange' will be null if the type no longer exists in the java;
		// the context model can be out of synch with the resource model
		// when a selection event occurs before the context model has a
		// chance to synch with the resource model via the update thread
		return (fullTextRange == null) ? false : fullTextRange.includes(offset);
	}

	protected TextRange getFullTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentType.getTextRange(astRoot);
	}

	public TextRange getSelectionTextRange() {
		return this.getSelectionTextRange(this.buildASTRoot());
	}

	protected TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentType.getNameTextRange(astRoot);
	}

	public void dispose() {
		this.unregisterRootStructureNode();
	}

	protected void registerRootStructureNode() {
		JpaFile jpaFile = this.getJpaFile();
		// the JPA file can be null if the resource type is "external"
		if (jpaFile != null) {
			jpaFile.addRootStructureNode(this.name, this);
		}
	}

	protected void unregisterRootStructureNode() {
		JpaFile jpaFile = this.getJpaFile();
		// the JPA file can be null if the .java file was deleted
		// or the resource type is "external"
		if (jpaFile != null) {
			jpaFile.removeRootStructureNode(this.name, this);
		}
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		Iterator<String> values = this.mapping.javaCompletionProposals(pos, filter, astRoot);
		if (values != null) {
			return values;
		}
		for (JavaPersistentAttribute attribute : this.getAttributes()) {
			values = attribute.javaCompletionProposals(pos, filter, astRoot);
			if (values != null) {
				return values;
			}
		}
		return EmptyIterator.instance();
	}


	// ********** validation **********

	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
		// TODO temporary hack since we don't know yet where to put
		// any messages for types in another project (e.g. referenced by
		// persistence.xml)
		IFile file = this.resourcePersistentType.getFile();
		// 'file' will be null if the type is "external" and binary;
		// the file will be in a different project if the type is "external" and source;
		// the type will be binary if it is in a JAR in the current project
		if ((file != null) && file.getProject().equals(this.getJpaProject().getProject()) &&
				(this.resourcePersistentType instanceof SourceNode)) {
			// build the AST root here to pass down
			this.validate(messages, reporter, this.buildASTRoot());
		}
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateMapping(messages, reporter, astRoot);
		this.validateAttributes(messages, reporter, astRoot);
	}

	protected void validateMapping(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		try {
			this.mapping.validate(messages, reporter, astRoot);
		} catch(Throwable t) {
			JptCorePlugin.log(t);
		}
	}

	protected void validateAttributes(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		for (JavaPersistentAttribute attribute : this.getAttributes()) {
			this.validateAttribute(attribute, reporter, messages, astRoot);
		}
	}

	protected void validateAttribute(JavaPersistentAttribute attribute, IReporter reporter, List<IMessage> messages, CompilationUnit astRoot) {
		try {
			attribute.validate(messages, reporter, astRoot);
		} catch(Throwable t) {
			JptCorePlugin.log(t);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getSelectionTextRange(astRoot);
	}


	// ********** misc **********

	@Override
	public PersistentType.Owner getParent() {
		return (PersistentType.Owner) super.getParent();
	}

	@Override
	public IResource getResource() {
		return this.resourcePersistentType.getFile();
	}

	public JavaResourcePersistentType getResourcePersistentType() {
		return this.resourcePersistentType;
	}

	public AccessType getOwnerOverrideAccess() {
		return this.getParent().getOverridePersistentTypeAccess();
	}

	public AccessType getOwnerDefaultAccess() {
		return this.getParent().getDefaultPersistentTypeAccess();
	}

	protected CompilationUnit buildASTRoot() {
		return this.resourcePersistentType.getJavaResourceCompilationUnit().buildASTRoot();
	}

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.resourcePersistentType.getFile());
	}

	public boolean isFor(String typeName) {
		return Tools.valuesAreEqual(typeName, this.getName());
	}

	public boolean isIn(IPackageFragment packageFragment) {
		return Tools.valuesAreEqual(packageFragment.getElementName(), this.getPackageName());
	}

	protected String getPackageName() {
		return this.getResourcePersistentType().getPackageName();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
