/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.content.IContentType;
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
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.resource.java.source.SourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
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

public abstract class AbstractJavaPersistentType
	extends AbstractJavaJpaContextNode
	implements JavaPersistentType
{
	protected JavaResourcePersistentType resourcePersistentType;
	
	protected PersistentType superPersistentType;
	
	protected String name;
	
	protected AccessType defaultAccess;

	protected AccessType specifiedAccess;
	
	protected JavaTypeMapping mapping;
	
	protected final Vector<JavaPersistentAttribute> attributes = 
			new Vector<JavaPersistentAttribute>();
	
	
	protected AbstractJavaPersistentType(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent);
		this.initialize(jrpt);
	}
	
	
	@Override
	public IResource getResource() {
		return this.resourcePersistentType.getFile();
	}
	
	
	//****************** JpaStructureNode implementation *******************
	
	public String getId() {
		return JavaStructureNodes.PERSISTENT_TYPE_ID;
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE;
	}
	
	
	//****************** PersistentType implementation *******************
	
	public JavaResourcePersistentType getResourcePersistentType() {
		return this.resourcePersistentType;
	}
	
	@Override
	public PersistentType.Owner getParent() {
		return (PersistentType.Owner) super.getParent();
	}
	
	
	// **************** name **************************************************
	
	public String getName() {
		return this.name;
	}
	
	public String getShortName(){
		return getName().substring(getName().lastIndexOf('.') + 1);
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	protected String buildName() {
		return this.resourcePersistentType.getQualifiedName();
	}
	
	
	// **************** access ************************************************
	
	public AccessType getAccess() {
		return getSpecifiedAccess() != null ? getSpecifiedAccess() : getDefaultAccess();
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}
	
	protected void setDefaultAccess(AccessType newDefaultAccess) {
		AccessType oldAccess = this.defaultAccess;
		this.defaultAccess = newDefaultAccess;
		firePropertyChanged(DEFAULT_ACCESS_PROPERTY, oldAccess, newDefaultAccess);
	}
	
	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}
	
	/**
	 * Check the access "specified" by the java resource model.
	 * 		Check java annotations first.
	 * 		If still null check xml mapping specified access
	 *		If still null then set to superPersistentType access.
	 * 		If still null check entity-mappings specified access setting if this persistent-type is listed in an orm.xml file
	 * 		If still null check the persistence-unit default Access
	 * 		Default to FIELD if all else fails.
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
			accessType = this.superPersistentType.getDefaultAccess();
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
	
	/**
	 * Build an access type based on annotations from the resource model.
	 * (This is platform dependent)
	 */
	protected abstract AccessType buildSpecifiedAccess();
	
	
	// **************** mapping ***********************************************
	
	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return getMapping().getKey();
	}
	
	public void setMappingKey(String key) {
		if (this.valuesAreEqual(key, this.getMapping().getKey())) {
			return;
		}
		JavaTypeMapping oldMapping = getMapping();
		JavaTypeMapping newMapping = buildMappingFromMappingKey(key);
		
		this.mapping = newMapping;
		this.resourcePersistentType.setPrimaryAnnotation(
				newMapping.getAnnotationName(),
				ArrayTools.array(newMapping.supportingAnnotationNames(), new String[0]));
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, newMapping);
	}
	
	protected void setMapping(JavaTypeMapping newMapping) {
		JavaTypeMapping oldMapping = this.mapping;
		this.mapping = newMapping;	
		firePropertyChanged(MAPPING_PROPERTY, oldMapping, newMapping);
	}
	
	protected JavaTypeMapping buildMapping() {
		JavaTypeMappingProvider mappingProvider = 
				getJpaPlatform().getJavaTypeMappingProvider(this);
		return buildMapping(mappingProvider);
	}
	
	protected JavaTypeMapping buildMapping(JavaTypeMappingProvider mappingProvider) {
		JavaTypeMapping jtMapping = mappingProvider.buildMapping(this, getJpaFactory());
		// mapping may be null
		if (jtMapping != null) {
			jtMapping.initialize(this.resourcePersistentType);
		}
		return jtMapping;
	}
	
	protected JavaTypeMapping buildMappingFromMappingKey(String key) {
		JavaTypeMappingProvider mappingProvider = getJpaPlatform().getJavaTypeMappingProvider(key);
		JavaTypeMapping jtMapping = mappingProvider.buildMapping(this, getJpaFactory());
		//no mapping.initialize(JavaResourcePersistentType) call here
		//we do not yet have a mapping annotation so we can't call initialize
		return jtMapping;
	}
	
	
	public boolean isMapped() {
		return getMapping().isMapped();
	}

	public AccessType getOwnerOverrideAccess() {
		return this.getParent().getOverridePersistentTypeAccess();
	}

	public AccessType getOwnerDefaultAccess() {
		return this.getParent().getDefaultPersistentTypeAccess();
	}

	protected Iterator<JavaPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<JavaPersistentAttribute, JavaPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(JavaPersistentAttribute o) {
				return attributeName.equals(o.getName());
			}
		};
	}

	public JavaPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = attributesNamed(attributeName);
		if (stream.hasNext()) {
			JavaPersistentAttribute attribute = stream.next();
			return (stream.hasNext()) ? null /*more than one*/: attribute;
		}
		return (this.superPersistentType == null) ? null : this.superPersistentType.resolveAttribute(attributeName);
	}
	
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
		addItemToList(index, attribute, this.attributes, ATTRIBUTES_LIST);
	}

	private void removeAttribute(JavaPersistentAttribute attribute) {
		removeItemFromList(attribute, this.attributes, ATTRIBUTES_LIST);
	}
	
	private void moveAttribute(int index, JavaPersistentAttribute attribute) {
		moveItemInList(index, this.attributes.indexOf(attribute), this.attributes, ATTRIBUTES_LIST);
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
	
	public Iterator<PersistentAttribute> allAttributes() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<PersistentType, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<PersistentAttribute> transform(PersistentType pt) {
				return pt.attributes();
			}
		});
	}
	
	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

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

	protected CompilationUnit buildASTRoot() {
		return this.resourcePersistentType.getJavaResourceCompilationUnit().buildASTRoot();
	}
	
	public boolean contains(int offset, CompilationUnit astRoot) {
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

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getSelectionTextRange(astRoot);
	}

	public TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentType.getNameTextRange(astRoot);
	}
	
	public TextRange getSelectionTextRange() {
		return this.getSelectionTextRange(this.buildASTRoot());
	}
	
	
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

	public PersistentType getSuperPersistentType() {
		return this.superPersistentType;
	}

	protected void setSuperPersistentType(PersistentType superPersistentType) {
		PersistentType old = this.superPersistentType;
		this.superPersistentType = superPersistentType;
		this.firePropertyChanged(SUPER_PERSISTENT_TYPE_PROPERTY, old, superPersistentType);
	}
	
	public boolean hasAnyAnnotatedAttributes() {
		return this.resourcePersistentType.hasAnyAnnotatedAttributes();
	}
	
	// **************** initialization / updating *****************************
	
	protected void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.superPersistentType = this.buildSuperPersistentType();
		this.name = this.buildName();
		this.defaultAccess = buildDefaultAccess();
		this.specifiedAccess = buildSpecifiedAccess();
		this.mapping = buildMapping();
		this.initializeAttributes();		
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

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.resourcePersistentType.getFile());
	}
	
	protected void updateAccess() {
		this.setDefaultAccess(this.buildDefaultAccess());
		
	}
	
	protected void updateMapping() {
		// There will always be a mapping provider, even if it is a "null" mapping provider ...
		JavaTypeMappingProvider mappingProvider = 
				getJpaPlatform().getJavaTypeMappingProvider(this);
		String mappingKey = mappingProvider.getKey();
		if (this.mapping != null
				&& valuesAreEqual(this.mapping.getKey(), mappingKey)) {
			this.mapping.update(this.resourcePersistentType);
		} 
		else {
			setMapping(buildMapping(mappingProvider));
		}
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
		return getJpaFactory().buildJavaPersistentAttribute(this, jrpa);
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

	protected PersistentType getPersistentType(String fullyQualifiedTypeName) {
		return getPersistenceUnit().getPersistentType(fullyQualifiedTypeName);
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		getMapping().postUpdate();
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
	

	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}

	public void dispose() {
		JpaFile jpaFile = this.getJpaFile();
		if (jpaFile != null) {
			// the JPA file can be null if the .java file was deleted
			// or the resource type is "external"
			jpaFile.removeRootStructureNode(this.resourcePersistentType.getQualifiedName());
		}
	}

}
