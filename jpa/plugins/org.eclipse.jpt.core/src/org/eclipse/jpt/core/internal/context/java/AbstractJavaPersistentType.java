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
import org.eclipse.core.resources.IResource;
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
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractJavaPersistentType
	extends AbstractJavaJpaContextNode
	implements JavaPersistentType
{
	protected String name;
	
	protected JavaTypeMapping mapping;

	protected final List<JavaPersistentAttribute> attributes;

	protected PersistentType parentPersistentType;

	protected JavaResourcePersistentType resourcePersistentType;

	protected AccessType defaultAccess;

	public AbstractJavaPersistentType(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent);
		this.attributes = new ArrayList<JavaPersistentAttribute>();
		this.initialize(jrpt);
	}
	
	@Override
	public IResource getResource() {
		return this.resourcePersistentType.getJavaResourceCompilationUnit().getFile();
	}

	//****************** JpaStructureNode implementation *******************
	
	public String getId() {
		return JavaStructureNodes.PERSISTENT_TYPE_ID;
	}
	
	//****************** PersistentType implementation *******************
	
	@Override
	public PersistentType.Owner getParent() {
		return (PersistentType.Owner) super.getParent();
	}
	
	//convenience since getParent is overloaded, confusing if this means containment parent
	//or inheritance parentPersistentType
	protected PersistentType.Owner getOwner() {
		return getParent();
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}
	
	protected void setDefaultAccess(AccessType newDefaultAccess) {
		this.defaultAccess = newDefaultAccess;
	}
	
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
	
	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return getMapping().getKey();
	}
	
	public void setMappingKey(String key) {
		if (key == getMapping().getKey()) {
			return;
		}
		JavaTypeMapping oldMapping = getMapping();
		JavaTypeMapping newMapping = createJavaTypeMappingFromMappingKey(key);
	
		this.mapping = newMapping;	
		this.resourcePersistentType.setMappingAnnotation(newMapping.getAnnotationName());
		firePropertyChanged(PersistentType.MAPPING_PROPERTY, oldMapping, newMapping);
	
		if (oldMapping != null) {
			Collection<String> annotationsToRemove = CollectionTools.collection(oldMapping.correspondingAnnotationNames());
			if (getMapping() != null) {
				CollectionTools.removeAll(annotationsToRemove, getMapping().correspondingAnnotationNames());
			}
			
			for (String annotationName : annotationsToRemove) {
				this.resourcePersistentType.removeSupportingAnnotation(annotationName);
			}
		}
	}
	
	protected void setMapping(JavaTypeMapping newMapping) {
		JavaTypeMapping oldMapping = this.mapping;
		this.mapping = newMapping;	
		firePropertyChanged(PersistentType.MAPPING_PROPERTY, oldMapping, newMapping);
	}
	
	public boolean isMapped() {
		return getMapping().isMapped();
	}

	public AccessType getOwnerOverrideAccess() {
		return this.getOwner().getOverridePersistentTypeAccess();
	}

	public AccessType getOwnerDefaultAccess() {
		return this.getOwner().getDefaultPersistentTypeAccess();
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
		return (this.parentPersistentType == null) ? null : this.parentPersistentType.resolveAttribute(attributeName);
	}
	
	public ListIterator<JavaPersistentAttribute> attributes() {
		return new CloneListIterator<JavaPersistentAttribute>(this.attributes);
	}
	
	public int attributesSize() {
		return this.attributes.size();
	}
	
	private void addAttribute(int index, JavaPersistentAttribute attribute) {
		addItemToList(index, attribute, this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	private void removeAttribute(JavaPersistentAttribute attribute) {
		removeItemFromList(attribute, this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}
	
	private void moveAttribute(int index, JavaPersistentAttribute attribute) {
		moveItemInList(index, this.attributes.indexOf(attribute), this.attributes, PersistentType.SPECIFIED_ATTRIBUTES_LIST);
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
		for (Iterator<JavaPersistentAttribute> stream = attributes(); stream.hasNext();) {
			values = stream.next().javaCompletionProposals(pos, filter, astRoot);
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
			for (Iterator<JavaPersistentAttribute> stream = this.attributes(); stream.hasNext();) {
				JavaPersistentAttribute persistentAttribute = stream.next();
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
		return inheritanceHierarchyOf(this);
	}

	public Iterator<PersistentType> ancestors() {
		return inheritanceHierarchyOf(this.parentPersistentType);
	}

	protected static Iterator<PersistentType> inheritanceHierarchyOf(PersistentType persistentType) {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<PersistentType>(persistentType) {
			@Override
			protected PersistentType nextLink(PersistentType pt) {
				return pt.getParentPersistentType();
			}
		};
	}

	public PersistentType getParentPersistentType() {
		return this.parentPersistentType;
	}

	public void setParentPersistentType(PersistentType newParentPersistentType) {
		if (attributeValueHasNotChanged(this.parentPersistentType, newParentPersistentType)) {
			return;
		}
		PersistentType oldParentPersistentType = this.parentPersistentType;
		this.parentPersistentType = newParentPersistentType;
		firePropertyChanged(PersistentType.PARENT_PERSISTENT_TYPE_PROPERTY, oldParentPersistentType, newParentPersistentType);
	}
	
	public boolean hasAnyAttributeMappingAnnotations() {
		return this.resourcePersistentType.hasAnyAttributeAnnotations();
	}
	
	// ******************** Updating **********************
	protected void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.parentPersistentType = this.buildParentPersistentType();
		this.defaultAccess = this.buildDefaultAccess();
		this.name = this.buildName();
		this.initializeMapping();
		this.initializePersistentAttributes();
	}
		
	protected void initializeMapping() {
		this.mapping = this.getJpaPlatform().buildJavaTypeMappingFromAnnotation(this.javaMappingAnnotationName(), this);
		this.mapping.initialize(this.resourcePersistentType);
	}
	
	protected void initializePersistentAttributes() {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.persistentAttributes(); stream.hasNext(); ) {
			this.attributes.add(this.createAttribute(stream.next()));
		}
	}

	protected Iterator<JavaResourcePersistentAttribute> persistentAttributes() {
		return (this.getAccess() == AccessType.PROPERTY) ?
				this.resourcePersistentType.persistableProperties() :
				this.resourcePersistentType.persistableFields();
	}

	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.getJpaFile(this.resourcePersistentType.getFile()).addRootStructureNode(this.resourcePersistentType.getQualifiedName(), this);
		this.setParentPersistentType(this.buildParentPersistentType());
		this.setDefaultAccess(this.buildDefaultAccess());
		this.setName(this.buildName());	
		this.updateMapping();
		this.updatePersistentAttributes();
	}
	
	/**
	 * Check the access "specified" by the java resource model.
	 * 		Check xml mapping specified access first
	 * 		If still null check java annotations if the xml is not metadata-complete = true
	 *		If still null then set to parentPersistentType access.
	 * 		If still null check entity-mappings specified access setting if this persistent-type is listed in an orm.xml file
	 * 		If still null check the persistence-unit default Access
	 * 		Default to FIELD if all else fails.
	 */
	protected AccessType buildDefaultAccess() {
		AccessType accessType = this.getOwnerOverrideAccess();
		if (accessType != null) {
			return accessType;
		}

		accessType = AccessType.fromJavaResourceModel(this.resourcePersistentType.getAccess());
		if (accessType != null) {
			return accessType;
		}

		if (this.parentPersistentType != null) {
			accessType = this.parentPersistentType.getDefaultAccess();
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
	
	protected String buildName() {
		return this.resourcePersistentType.getQualifiedName();
	}
	
	protected void updateMapping() {
		String javaMappingAnnotationName = this.javaMappingAnnotationName();
		if (this.getMapping().getAnnotationName() == javaMappingAnnotationName) {
			this.getMapping().update(this.resourcePersistentType);
		} else {
			this.setMapping(this.createJavaTypeMappingFromAnnotation(javaMappingAnnotationName));
		}
	}
	
	protected JavaTypeMapping createJavaTypeMappingFromMappingKey(String key) {
		return getJpaPlatform().buildJavaTypeMappingFromMappingKey(key, this);
	}
	
	protected JavaTypeMapping createJavaTypeMappingFromAnnotation(String annotationName) {
		JavaTypeMapping typeMapping = getJpaPlatform().buildJavaTypeMappingFromAnnotation(annotationName, this);
		typeMapping.initialize(this.resourcePersistentType);
		return typeMapping;
	}

	protected String javaMappingAnnotationName() {
		Annotation mappingAnnotation = (Annotation) this.resourcePersistentType.getMappingAnnotation();
		return (mappingAnnotation == null) ? null :  mappingAnnotation.getAnnotationName();
	}

	protected void updatePersistentAttributes() {
		Collection<JavaPersistentAttribute> contextAttributesToRemove = CollectionTools.collection(attributes());
		Collection<JavaPersistentAttribute> contextAttributesToUpdate = new ArrayList<JavaPersistentAttribute>();
		int resourceIndex = 0;
		
		for (Iterator<JavaResourcePersistentAttribute> stream = this.persistentAttributes(); stream.hasNext(); ) {
			JavaResourcePersistentAttribute resourceAttribute = stream.next();
			boolean contextAttributeFound = false;
			for (JavaPersistentAttribute contextAttribute : contextAttributesToRemove) {
				if (contextAttribute.getResourcePersistentAttribute() == resourceAttribute) {
					moveAttribute(resourceIndex, contextAttribute);
					contextAttributesToRemove.remove(contextAttribute);
					contextAttributesToUpdate.add(contextAttribute);
					contextAttributeFound = true;
					break;
				}
			}
			if (!contextAttributeFound) {
				addAttribute(resourceIndex, createAttribute(resourceAttribute));
			}
			resourceIndex++;
		}
		for (JavaPersistentAttribute contextAttribute : contextAttributesToRemove) {
			removeAttribute(contextAttribute);
		}
		//first handle adding/removing of the persistent attributes, then update the others last, 
		//this causes less churn in the update process
		for (JavaPersistentAttribute contextAttribute : contextAttributesToUpdate) {
			contextAttribute.update();
		}
	}
	
	protected JavaPersistentAttribute createAttribute(JavaResourcePersistentAttribute jrpa) {
		return getJpaFactory().buildJavaPersistentAttribute(this, jrpa);
	}

	protected PersistentType buildParentPersistentType() {
		HashSet<JavaResourcePersistentType> visitedResourceTypes = new HashSet<JavaResourcePersistentType>();
		visitedResourceTypes.add(this.resourcePersistentType);
		PersistentType parent = this.getParent(this.resourcePersistentType.getSuperClassQualifiedName(), visitedResourceTypes);
		if (parent == null) {
			return null;
		}
		if (CollectionTools.contains(parent.inheritanceHierarchy(), this)) {
			return null;  // short-circuit in this case, we have circular inheritance
		}
		return parent.isMapped() ? parent : parent.getParentPersistentType();
	}

	/**
	 * The JPA spec allows non-persistent types in a persistent type's
	 * inheritance hierarchy. We check for a persistent type with the
	 * specified name in the persistence unit. If it is not found we use
	 * resource persistent type and look for *its* parent type.
	 * 
	 * 'visitedResourceTypes' is used to detect a cycle in the resource type
	 * inheritance hierarchy and prevent the resulting stack overflow.
	 * Any cycles in the persistent type inheritance hierarchy are handled in
	 * #buildParentPersistentType().
	 */
	protected PersistentType getParent(String typeName, Collection<JavaResourcePersistentType> visitedResourceTypes) {
		JavaResourcePersistentType resourceType = this.getJpaProject().getJavaResourcePersistentType(typeName);
		if ((resourceType == null) || visitedResourceTypes.contains(resourceType)) {
			return null;
		}
		visitedResourceTypes.add(resourceType);
		PersistentType parent = this.getPersistentType(typeName);
		return (parent != null) ? parent : this.getParent(resourceType.getSuperClassQualifiedName(), visitedResourceTypes);  // recurse
	}

	protected PersistentType getPersistentType(String fullyQualifiedTypeName) {
		return getPersistenceUnit().getPersistentType(fullyQualifiedTypeName);
	}


	// ********** validation **********

	public void validate(List<IMessage> messages) {
		// build the AST root here to pass down
		this.validate(messages, this.buildASTRoot());	
	}
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.validateMapping(messages, astRoot);
		this.validateAttributes(messages, astRoot);
	}
	
	protected void validateMapping(List<IMessage> messages, CompilationUnit astRoot) {
		try {
			this.mapping.validate(messages, astRoot);
		} catch(Throwable t) {
			JptCorePlugin.log(t);
		}
	}
	
	protected void validateAttributes(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaPersistentAttribute> stream = this.attributes(); stream.hasNext(); ) {
			this.validateAttribute(stream.next(), messages, astRoot);
		}
	}
	
	protected void validateAttribute(JavaPersistentAttribute attribute, List<IMessage> messages, CompilationUnit astRoot) {
		try {
			attribute.validate(messages, astRoot);
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
		JpaFile jpaFile = getJpaFile(this.resourcePersistentType.getFile());
		if (jpaFile != null) {
			// the JPA file can be null if the .java file was deleted
			jpaFile.removeRootStructureNode(this.resourcePersistentType.getQualifiedName());
		}
	}

}
