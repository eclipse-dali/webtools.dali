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

import java.util.ArrayList;
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
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
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
	protected JavaResourcePersistentType resourcePersistentType;

	protected String name;

	protected AccessType defaultAccess;

	protected AccessType specifiedAccess;

	protected JavaTypeMapping mapping;

	protected final Vector<JavaPersistentAttribute> attributes = new Vector<JavaPersistentAttribute>();

	protected PersistentType superPersistentType;


	protected AbstractJavaPersistentType(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent);
		this.initialize(jrpt);
	}

	protected void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.superPersistentType = this.buildSuperPersistentType();
		this.name = this.buildName();
		this.defaultAccess = buildDefaultAccess();
		this.specifiedAccess = buildSpecifiedAccess();
		this.mapping = buildMapping();
		this.initializeAttributes();
	}


	// ********** update **********

	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.update();
	}

	public void update() {
		JpaFile jpaFile = this.getJpaFile();
		if (jpaFile != null) {
			// the JPA file can be null if the resource type is "external"
			jpaFile.addRootStructureNode(this.resourcePersistentType.getQualifiedName(), this);
		}
		this.setSuperPersistentType(this.buildSuperPersistentType());
		this.setName(this.buildName());
		this.updateAccess();
		this.updateMapping();
		this.updateAttributes();
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		this.mapping.postUpdate();
		for (PersistentAttribute attribute : this.getAttributes()) {
			attribute.postUpdate();
		}
	}


	// ********** AbstractJpaNode overrides **********

	@Override
	public PersistentType.Owner getParent() {
		return (PersistentType.Owner) super.getParent();
	}

	@Override
	public IResource getResource() {
		return this.resourcePersistentType.getFile();
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
		JpaFile jpaFile = this.getJpaFile();
		if (jpaFile != null) {
			// the JPA file can be null if the .java file was deleted
			// or the resource type is "external"
			jpaFile.removeRootStructureNode(this.resourcePersistentType.getQualifiedName());
		}
	}


	// ********** PersistentType implementation **********

	public JavaResourcePersistentType getResourcePersistentType() {
		return this.resourcePersistentType;
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public String getShortName(){
		return this.name.substring(this.name.lastIndexOf('.') + 1);
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	protected String buildName() {
		return this.resourcePersistentType.getQualifiedName();
	}


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType defaultAccess) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = defaultAccess;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, defaultAccess);
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	/**
	 * Check the access "specified" by the Java resource model:<ul>
	 * <li>Check Java annotations first
	 * <li>If <code>null</code>, check XML mapping specified access
	 *	<li>If still <code>null</code>, check {@link #superPersistentType} access
	 * <li>If still <code>null</code>, check <code>entity-mappings</code>
	 *     specified access setting if the corresponding <code>persistent-type</code>
	 *     is listed in a mapping (<code>orm.xml</code>) file
	 * <li>If still <code>null</code>, check the <code>persistence-unit</code>
	 *     default Access
	 * <li>Default to <code>FIELD</code> if all else fails.
	 * </ul>
	 */
	protected AccessType buildDefaultAccess() {
		AccessType accessType = AccessType.fromJavaResourceModel(this.resourcePersistentType.getAccess());
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

	protected void updateAccess() {
		this.setDefaultAccess(this.buildDefaultAccess());
	}

	/**
	 * Build an access type based on annotations from the resource model.
	 * (This is JPA platform-dependent.)
	 */
	protected abstract AccessType buildSpecifiedAccess();


	// ********** mapping **********

	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String key) {
		if (this.valuesAreEqual(key, this.mapping.getKey())) {
			return;
		}
		JavaTypeMapping oldMapping = this.mapping;
		JavaTypeMapping newMapping = this.buildMappingFromMappingKey(key);

		this.mapping = newMapping;
		this.resourcePersistentType.setPrimaryAnnotation(newMapping.getAnnotationName(), newMapping.getSupportingAnnotationNames());
		this.firePropertyChanged(MAPPING_PROPERTY, oldMapping, newMapping);
	}

	protected void setMapping(JavaTypeMapping mapping) {
		JavaTypeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(MAPPING_PROPERTY, old, mapping);
	}

	protected JavaTypeMapping buildMapping() {
		JavaTypeMappingDefinition mappingDefinition = this.getJpaPlatform().getJavaTypeMappingDefinition(this);
		return this.buildMapping(mappingDefinition);
	}

	protected JavaTypeMapping buildMapping(JavaTypeMappingDefinition mappingDefinition) {
		JavaTypeMapping jtMapping = mappingDefinition.buildMapping(this, this.getJpaFactory());
		// mapping may be null
		if (jtMapping != null) {
			jtMapping.initialize(this.resourcePersistentType);
		}
		return jtMapping;
	}

	protected JavaTypeMapping buildMappingFromMappingKey(String key) {
		JavaTypeMappingDefinition mappingDefinition = this.getJpaPlatform().getJavaTypeMappingDefinition(key);
		JavaTypeMapping jtMapping = mappingDefinition.buildMapping(this, this.getJpaFactory());
		//no mapping.initialize(JavaResourcePersistentType) call here
		//we do not yet have a mapping annotation so we can't call initialize
		return jtMapping;
	}

	protected void updateMapping() {
		// There will always be a mapping definition, even if it is a "null" mapping definition ...
		JavaTypeMappingDefinition mappingDefinition = this.getJpaPlatform().getJavaTypeMappingDefinition(this);
		if ((this.mapping != null) && this.valuesAreEqual(this.mapping.getKey(), mappingDefinition.getKey())) {
			this.mapping.update(this.resourcePersistentType);
		}  else {
			this.setMapping(this.buildMapping(mappingDefinition));
		}
	}


	// ********** attributes **********

	public ListIterator<JavaPersistentAttribute> attributes() {
		return new CloneListIterator<JavaPersistentAttribute>(this.attributes);
	}

	protected Iterable<JavaPersistentAttribute> getAttributes() {
		return new LiveCloneIterable<JavaPersistentAttribute>(this.attributes);
	}

	public int attributesSize() {
		return this.attributes.size();
	}

	private void addAttribute(int index, JavaPersistentAttribute attribute) {
		this.addItemToList(index, attribute, this.attributes, ATTRIBUTES_LIST);
	}

	private void removeAttribute(JavaPersistentAttribute attribute) {
		this.removeItemFromList(attribute, this.attributes, ATTRIBUTES_LIST);
	}

	private void moveAttribute(int index, JavaPersistentAttribute attribute) {
		this.moveItemInList(index, this.attributes.indexOf(attribute), this.attributes, ATTRIBUTES_LIST);
	}

	public Iterator<String> attributeNames() {
		return this.attributeNames(this.attributes());
	}

	protected Iterator<String> attributeNames(Iterator<? extends PersistentAttribute> attrs) {
		return new TransformationIterator<PersistentAttribute, String>(attrs) {
			@Override
			protected String transform(PersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	public JavaPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = this.attributesNamed(attributeName);
		return stream.hasNext() ? stream.next() : null;
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = this.attributesNamed(attributeName);
		if (stream.hasNext()) {
			JavaPersistentAttribute attribute = stream.next();
			return stream.hasNext() ? null /*more than one*/: attribute;
		}
		return (this.superPersistentType == null) ? null : this.superPersistentType.resolveAttribute(attributeName);
	}

	protected Iterator<JavaPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<JavaPersistentAttribute>(this.attributes()) {
			@Override
			protected boolean accept(JavaPersistentAttribute o) {
				return attributeName.equals(o.getName());
			}
		};
	}

	public Iterator<PersistentAttribute> allAttributes() {
		return new CompositeIterator<PersistentAttribute>(
				new TransformationIterator<PersistentType, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
					@Override
					protected Iterator<PersistentAttribute> transform(PersistentType pt) {
						return pt.attributes();
					}
				}
			);
	}

	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

	protected void initializeAttributes() {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.resourceAttributes(); stream.hasNext(); ) {
			this.attributes.add(this.createAttribute(stream.next()));
		}
	}

	protected Iterator<JavaResourcePersistentAttribute> resourceAttributes() {
		return (this.getAccess() == AccessType.PROPERTY) ?
				this.resourcePersistentType.persistableProperties() :
				this.resourcePersistentType.persistableFields();
	}

	protected void updateAttributes() {
		HashBag<JavaPersistentAttribute> contextAttributesToRemove = CollectionTools.bag(this.attributes(), this.attributesSize());
		ArrayList<JavaPersistentAttribute> contextAttributesToUpdate = new ArrayList<JavaPersistentAttribute>(this.attributesSize());
		int resourceIndex = 0;

		for (Iterator<JavaResourcePersistentAttribute> resourceAttributes = this.resourceAttributes(); resourceAttributes.hasNext(); ) {
			JavaResourcePersistentAttribute resourceAttribute = resourceAttributes.next();
			boolean match = false;
			for (Iterator<JavaPersistentAttribute> contextAttributes = contextAttributesToRemove.iterator(); contextAttributes.hasNext(); ) {
				JavaPersistentAttribute contextAttribute = contextAttributes.next();
				if (contextAttribute.getResourcePersistentAttribute() == resourceAttribute) {
					this.moveAttribute(resourceIndex, contextAttribute);
					contextAttributes.remove();
					contextAttributesToUpdate.add(contextAttribute);
					match = true;
					break;
				}
			}
			if ( ! match) {
				this.addAttribute(resourceIndex, this.createAttribute(resourceAttribute));
			}
			resourceIndex++;
		}
		for (JavaPersistentAttribute contextAttribute : contextAttributesToRemove) {
			this.removeAttribute(contextAttribute);
		}
		// handle adding and removing attributes first, update the
		// remaining attributes last; this reduces the churn during "update"
		for (JavaPersistentAttribute contextAttribute : contextAttributesToUpdate) {
			contextAttribute.update();
		}
	}

	protected JavaPersistentAttribute createAttribute(JavaResourcePersistentAttribute jrpa) {
		return this.getJpaFactory().buildJavaPersistentAttribute(this, jrpa);
	}

	public boolean hasAnyAnnotatedAttributes() {
		return this.resourcePersistentType.hasAnyAnnotatedAttributes();
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
		PersistentType spt = this.getSuperPersistentType(this.resourcePersistentType.getSuperclassQualifiedName(), visited);
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
	 * resource persistent type and look for *its* super type.
	 * 
	 * The 'visited' collection is used to detect a cycle in the *resource* type
	 * inheritance hierarchy and prevent the resulting stack overflow.
	 * Any cycles in the *context* type inheritance hierarchy are handled in
	 * #buildSuperPersistentType().
	 */
	protected PersistentType getSuperPersistentType(String typeName, Collection<JavaResourcePersistentType> visited) {
		if (typeName == null) {
			return null;
		}
		JavaResourcePersistentType resourceType = this.getJpaProject().getJavaResourcePersistentType(typeName);
		if ((resourceType == null) || visited.contains(resourceType)) {
			return null;
		}
		visited.add(resourceType);
		PersistentType spt = this.getPersistentType(typeName);
		return (spt != null) ? spt : this.getSuperPersistentType(resourceType.getSuperclassQualifiedName(), visited);  // recurse
	}

	protected PersistentType getPersistentType(String typeName) {
		return this.getPersistenceUnit().getPersistentType(typeName);
	}


	// ********** inheritance **********

	public Iterator<PersistentType> inheritanceHierarchy() {
		return this.inheritanceHierarchyOf(this);
	}

	public Iterator<PersistentType> ancestors() {
		return this.inheritanceHierarchyOf(this.superPersistentType);
	}

	protected Iterator<PersistentType> inheritanceHierarchyOf(PersistentType start) {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(start) {
			@Override
			protected PersistentType nextLink(PersistentType persistentType) {
				return persistentType.getSuperPersistentType();
			}
		};
	}


	// ********** code completion  **********

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

	public boolean isFor(String typeName) {
		String className = this.getName();
		return className != null && className.equals(typeName);
	}

	public boolean isIn(IPackageFragment packageFragment) {
		String packageName = this.getPackageName();
		if (packageName != null && packageName.equals(packageFragment.getElementName())) {
			return true;
		}
		return false;
	}

	protected String getPackageName() {
		return getResourcePersistentType().getPackageName();
	}

	public boolean isMapped() {
		return this.mapping.isMapped();
	}

	public AccessType getOwnerOverrideAccess() {
		return this.getParent().getOverridePersistentTypeAccess();
	}

	public AccessType getOwnerDefaultAccess() {
		return this.getParent().getDefaultPersistentTypeAccess();
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}

	protected CompilationUnit buildASTRoot() {
		return this.resourcePersistentType.getJavaResourceCompilationUnit().buildASTRoot();
	}

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.resourcePersistentType.getFile());
	}

}
