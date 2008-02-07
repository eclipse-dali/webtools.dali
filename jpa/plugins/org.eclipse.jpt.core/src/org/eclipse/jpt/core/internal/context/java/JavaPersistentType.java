/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IJpaStructureNode;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Annotation;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ChainIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaPersistentType extends JavaContextModel implements IJavaPersistentType
{
	protected String name;
	
	protected IJavaTypeMapping mapping;

	protected final List<IJavaPersistentAttribute> attributes;

	protected AccessType access;

	protected IPersistentType parentPersistentType;

	protected JavaPersistentTypeResource persistentTypeResource;

	public JavaPersistentType(IJpaContextNode parent) {
		super(parent);
		this.attributes = new ArrayList<IJavaPersistentAttribute>();
	}
	
	public String getId() {
		return IJavaStructureNodes.PERSISTENT_TYPE_ID;
	}
	
	public void initializeFromResource(JavaPersistentTypeResource persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
		this.parentPersistentType = this.parentPersistentType(persistentTypeResource);
		this.access = this.access(persistentTypeResource);
		this.name = this.name(persistentTypeResource);
		this.initializeMapping(persistentTypeResource);
		this.initializePersistentAttributes(persistentTypeResource);
	}
	
	protected void initializeMapping(JavaPersistentTypeResource persistentTypeResource) {
		this.mapping  = jpaPlatform().createJavaTypeMappingFromAnnotation(this.javaMappingAnnotationName(persistentTypeResource), this);
		this.mapping.initializeFromResource(persistentTypeResource);
	}
	
	protected void initializePersistentAttributes(JavaPersistentTypeResource persistentTypeResource) {
		Iterator<JavaPersistentAttributeResource> resourceAttributes = persistentTypeResource.fields();
		if (access() == AccessType.PROPERTY) {
			resourceAttributes = persistentTypeResource.properties();
		}		
		
		while (resourceAttributes.hasNext()) {
			this.attributes.add(createAttribute(resourceAttributes.next()));
		}
	}
	
	@Override
	public IResource resource() {
		return this.persistentTypeResource.resourceModel().resource().getCompilationUnit().getResource();
	}

	
	//****************** IPersistentType implementation *******************
	public String getName() {
		return this.name;
	}
	
	protected void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public IJavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String mappingKey() {
		return getMapping().getKey();
	}
	
	public void setMappingKey(String key) {
		if (key == getMapping().getKey()) {
			return;
		}
		IJavaTypeMapping oldMapping = getMapping();
		IJavaTypeMapping newMapping = createJavaTypeMappingFromMappingKey(key);
		setMapping(newMapping);		
		this.persistentTypeResource.setMappingAnnotation(newMapping.annotationName());
		
		if (oldMapping != null) {
			Collection<String> annotationsToRemove = CollectionTools.collection(oldMapping.correspondingAnnotationNames());
			if (getMapping() != null) {
				CollectionTools.removeAll(annotationsToRemove, getMapping().correspondingAnnotationNames());
			}
			
			for (String annotationName : annotationsToRemove) {
				this.persistentTypeResource.removeAnnotation(annotationName);
			}
		}
	}
	
	protected void setMapping(IJavaTypeMapping newMapping) {
		IJavaTypeMapping oldMapping = this.mapping;
		this.mapping = newMapping;	
		firePropertyChanged(IPersistentType.MAPPING_PROPERTY, oldMapping, newMapping);
	}
	
	public boolean isMapped() {
		return getMapping().isMapped();
	}

	public AccessType access() {
		return this.access;
	}
	
	protected void setAccess(AccessType newAccess) {
		AccessType oldAccess = this.access;
		this.access = newAccess;
		firePropertyChanged(IPersistentType.ACCESS_PROPERTY, oldAccess, newAccess);
	}

	protected Iterator<IJavaPersistentAttribute> attributesNamed(final String attributeName) {
		return new FilteringIterator<IJavaPersistentAttribute, IJavaPersistentAttribute>(attributes()) {
			@Override
			protected boolean accept(IJavaPersistentAttribute o) {
				return attributeName.equals(o.getName());
			}
		};
	}

	public IJavaPersistentAttribute attributeNamed(String attributeName) {
		Iterator<IJavaPersistentAttribute> stream = attributesNamed(attributeName);
		return (stream.hasNext()) ? stream.next() : null;
	}

	public IPersistentAttribute resolveAttribute(String attributeName) {
		Iterator<IJavaPersistentAttribute> stream = attributesNamed(attributeName);
		if (stream.hasNext()) {
			IJavaPersistentAttribute attribute = stream.next();
			return (stream.hasNext()) ? null /*more than one*/: attribute;
		}
		return (parentPersistentType() == null) ? null : parentPersistentType().resolveAttribute(attributeName);
	}
	
	public ListIterator<IJavaPersistentAttribute> attributes() {
		return new CloneListIterator<IJavaPersistentAttribute>(this.attributes);
	}
	
	public int attributesSize() {
		return this.attributes.size();
	}
	
	private void addAttribute(IJavaPersistentAttribute attribute) {
		addItemToList(attribute, this.attributes, IPersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}

	private void removeAttribute(IJavaPersistentAttribute attribute) {
		removeItemFromList(attribute, this.attributes, IPersistentType.SPECIFIED_ATTRIBUTES_LIST);
	}
	
	public Iterator<String> attributeNames() {
		return this.attributeNames(this.attributes());
	}
	
	protected Iterator<String> attributeNames(Iterator<? extends IPersistentAttribute> attrs) {
		return new TransformationIterator<IPersistentAttribute, String>(attrs) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}
	
	public Iterator<IPersistentAttribute> allAttributes() {
		return new CompositeIterator<IPersistentAttribute>(new TransformationIterator<IPersistentType, Iterator<IPersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<IPersistentAttribute> transform(IPersistentType pt) {
				return pt.attributes();
			}
		});
	}
	
	public Iterator<String> allAttributeNames() {
		return this.attributeNames(this.allAttributes());
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		Iterator<String> values = this.mapping.candidateValuesFor(pos, filter, astRoot);
		if (values != null) {
			return values;
		}
		for (Iterator<IJavaPersistentAttribute> stream = attributes(); stream.hasNext();) {
			values = stream.next().candidateValuesFor(pos, filter, astRoot);
			if (values != null) {
				return values;
			}
		}
		return EmptyIterator.instance();
	}
	
	@Override
	public IJpaStructureNode structureNode(int offset) {
		//TODO astRoot, possibly get this instead of rebuilding it
		CompilationUnit astRoot = this.persistentTypeResource.getMember().astRoot(); 
		if (!this.contains(offset, astRoot)) {
			return null;
		}
		
		for (Iterator<IJavaPersistentAttribute> i = attributes(); i.hasNext();) {
			IJavaPersistentAttribute persistentAttribute = i.next();
			if (persistentAttribute.contains(offset, astRoot)) {
				return persistentAttribute;
			}
		}
		
		return this;		
	}

	public boolean contains(int offset, CompilationUnit astRoot) {
		ITextRange fullTextRange = this.fullTextRange(astRoot);
		if (fullTextRange == null) {
			//This happens if the attribute no longer exists in the java.
			//The text selection event is fired before the update from java so our
			//model has not yet had a chance to update appropriately. The list of
			//JavaPersistentAttriubtes is stale at this point.  For now, we are trying
			//to avoid the NPE, not sure of the ultimate solution to these 2 threads accessing
			//our model
			return false;
		}
		return fullTextRange.includes(offset);
	}


	public ITextRange fullTextRange(CompilationUnit astRoot) {
		return this.persistentTypeResource.textRange(astRoot);
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		return this.selectionTextRange(astRoot);
	}

	public ITextRange selectionTextRange(CompilationUnit astRoot) {
		return this.persistentTypeResource.nameTextRange(astRoot);
	}
	
	public ITextRange selectionTextRange() {
		return this.selectionTextRange(this.persistentTypeResource.getMember().astRoot());
	}
	
	
	public Iterator<IPersistentType> inheritanceHierarchy() {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterator<IPersistentType>(this) {
			@Override
			protected IPersistentType nextLink(IPersistentType pt) {
				return pt.parentPersistentType();
			}
		};
	}

	public IPersistentType parentPersistentType() {
		return this.parentPersistentType;
	}

	public boolean hasAnyAttributeMappingAnnotations() {
		if (this.persistentTypeResource.hasAnyAttributeAnnotations()) {
			return true;
		}
		return false;
	}
	
	// ******************** Updating **********************
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
		this.persistentTypeResource.resourceModel().addRootContextNode(this);
		updateParentPersistentType(persistentTypeResource);
		updateAccess(persistentTypeResource);
		updateName(persistentTypeResource);
		updateMapping(persistentTypeResource);
		updatePersistentAttributes(persistentTypeResource);
	}
	
	protected void updateAccess(JavaPersistentTypeResource persistentTypeResource) {
		this.setAccess(this.access(persistentTypeResource));
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
	protected AccessType access(JavaPersistentTypeResource persistentTypeResource) {
		AccessType javaAccess = null;
		boolean metadataComplete = false;
		if (xmlPersistentType() != null) {
			javaAccess = xmlPersistentType().getMapping().getSpecifiedAccess();
			metadataComplete = xmlPersistentType().getMapping().isMetadataComplete();
		}
		if (javaAccess == null && !metadataComplete) {
			javaAccess = AccessType.fromJavaResourceModel(persistentTypeResource.getAccess());
		}
		if (javaAccess == null) {
			if (parentPersistentType() != null) {
				javaAccess = parentPersistentType().access();
			}
		}
		if (javaAccess == null) {
			if (entityMappings() != null) {
				javaAccess = entityMappings().getAccess();
			}
		}
		if (javaAccess == null) {
			//have to check persistence-unit separately in the case where it is not listed directly in an orm.xml
			//if it is listed in an orm.xml then the entityMappings().getAccess() check will cover persistence-unit.defaultAccess
			if (persistenceUnit() != null) {
				javaAccess = persistenceUnit().getDefaultAccess();
			}
		}
		if (javaAccess == null) {
			javaAccess = AccessType.FIELD;
		}
		return javaAccess;
	}
	
	protected void updateName(JavaPersistentTypeResource persistentTypeResource) {
		this.setName(this.name(persistentTypeResource));	
	}
	
	protected String name(JavaPersistentTypeResource persistentTypeResource) {
		return persistentTypeResource.getQualifiedName();
	}
	
	protected void updateMapping(JavaPersistentTypeResource persistentTypeResource) {
		String javaMappingAnnotationName = this.javaMappingAnnotationName(persistentTypeResource);
		if (getMapping().annotationName() != javaMappingAnnotationName) {
			setMapping(createJavaTypeMappingFromAnnotation(javaMappingAnnotationName, persistentTypeResource));
		}
		else {
			getMapping().update(persistentTypeResource);
		}
	}
	
	protected IJavaTypeMapping createJavaTypeMappingFromMappingKey(String key) {
		return jpaPlatform().createJavaTypeMappingFromMappingKey(key, this);
	}
	
	protected IJavaTypeMapping createJavaTypeMappingFromAnnotation(String annotationName, JavaPersistentTypeResource persistentTypeResource) {
		IJavaTypeMapping mapping = jpaPlatform().createJavaTypeMappingFromAnnotation(annotationName, this);
		mapping.initializeFromResource(persistentTypeResource);
		return mapping;
	}

	protected String javaMappingAnnotationName(JavaPersistentTypeResource typeResource) {
		Annotation mappingAnnotation = (Annotation) typeResource.mappingAnnotation();
		if (mappingAnnotation != null) {
			return mappingAnnotation.getAnnotationName();
		}
		return null;
	}

	protected void updatePersistentAttributes(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaPersistentAttribute> contextAttributes = attributes();
		Iterator<JavaPersistentAttributeResource> resourceAttributes = persistentTypeResource.fields();
		if (access() == AccessType.PROPERTY) {
			resourceAttributes = persistentTypeResource.properties();
		}		
		
		while (contextAttributes.hasNext()) {
			IJavaPersistentAttribute persistentAttribute = contextAttributes.next();
			if (resourceAttributes.hasNext()) {
				persistentAttribute.update(resourceAttributes.next());
			}
			else {
				removeAttribute(persistentAttribute);
			}
		}
		
		while (resourceAttributes.hasNext()) {
			addAttribute(createAttribute(resourceAttributes.next()));
		}
	}
	
	protected IJavaPersistentAttribute createAttribute(JavaPersistentAttributeResource persistentAttributeResource) {
		IJavaPersistentAttribute javaPersistentAttribute = jpaFactory().createJavaPersistentAttribute(this);
		javaPersistentAttribute.initializeFromResource(persistentAttributeResource);
		return javaPersistentAttribute;
	}
	
	public void updateParentPersistentType(JavaPersistentTypeResource persistentTypeResource) {
		//TODO do we need any change notification for this?
		this.parentPersistentType = parentPersistentType(persistentTypeResource);
	}
	
	protected IPersistentType parentPersistentType(JavaPersistentTypeResource persistentTypeResource) {
		return parentPersistentType(persistentTypeResource.getSuperClassQualifiedName());
	}
	
	protected IPersistentType parentPersistentType(String fullyQualifiedTypeName) {
		IPersistentType possibleParent = possibleParent(fullyQualifiedTypeName);
		if (possibleParent == null) {
			return null;
		}
		if (possibleParent.isMapped()) {
			return possibleParent;
		}
		return possibleParent.parentPersistentType();
	}

	/**
	 * JPA spec supports the case where there are non-persistent types in the hierarchy
	 * This will check for a PersistentType with the given name in this PersistenceUnit.
	 * If it is not found then find the JavaPersistentTypeResource and look for its parent type
	 */
	protected IPersistentType possibleParent(String fullyQualifiedTypeName) {
		IPersistentType possibleParent = persistentType(fullyQualifiedTypeName);
		if (possibleParent != null) {
			return possibleParent;
		}
		JavaPersistentTypeResource javaPersistentTypeResource = jpaProject().javaPersistentTypeResource(fullyQualifiedTypeName);
		if (javaPersistentTypeResource != null) {
			return possibleParent(javaPersistentTypeResource.getSuperClassQualifiedName());
		}
		return null;		
	}
	
	protected IPersistentType persistentType(String fullyQualifiedTypeName) {
		return persistenceUnit().persistentType(fullyQualifiedTypeName);
	}

	//*************** Validation ******************************************
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
	
		//get astRoot here to pass down
		astRoot = persistentTypeResource.getMember().astRoot();
		mapping.addToMessages(messages, astRoot);
		
		addAttributeMessages(messages, astRoot);
		
	}
	
	protected void addAttributeMessages(List<IMessage> messages, CompilationUnit astRoot) {
		for (IJavaPersistentAttribute attributeContext : this.attributes) {
			attributeContext.addToMessages(messages, astRoot);
		}
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}

}