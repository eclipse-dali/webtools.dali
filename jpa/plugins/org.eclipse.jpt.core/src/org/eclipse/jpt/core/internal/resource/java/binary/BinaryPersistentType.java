/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.jdt.JPTTools;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * binary persistent type
 */
final class BinaryPersistentType
	extends BinaryPersistentMember
	implements JavaResourcePersistentType
{
	private String name;

	private String qualifiedName;

	private String superclassQualifiedName;

	private boolean abstract_;  // 'abstract' is a reserved word

	private final Vector<JavaResourcePersistentAttribute> fields;

	private final Vector<JavaResourcePersistentAttribute> methods;

	private AccessType access;


	// ********** construction/initialization **********

	BinaryPersistentType(JavaResourceNode parent, IType type) {
		super(parent, new TypeAdapter(type));
		this.name = this.buildName();
		this.qualifiedName = this.buildQualifiedName();
		this.superclassQualifiedName = this.buildSuperclassQualifiedName();
		this.abstract_ = this.buildAbstract();
		this.fields = this.buildFields();
		this.methods = this.buildMethods();
		// need to wait until everything is built to calculate 'access'
		this.access = this.buildAccess();
	}


	// ********** overrides **********

	@Override
	public void update() {
		super.update();
		this.setName(this.buildName());
		this.setQualifiedName(this.buildQualifiedName());
		this.setSuperclassQualifiedName(this.buildSuperclassQualifiedName());
		this.setAbstract(this.buildAbstract());
		this.updateFields();
		this.updateMethods();
		// need to wait until everything is updated to calculate 'access'
		this.setAccess(this.buildAccess());
	}

	// TODO
	private void updateFields() {
		throw new UnsupportedOperationException();
	}

	// TODO
	private void updateMethods() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** BinaryPersistentMember implementation **********

	@Override
	Annotation buildMappingAnnotation(IAnnotation jdtAnnotation) {
		return this.getAnnotationProvider().buildTypeMappingAnnotation(this, jdtAnnotation);
	}
	
	@Override
	Annotation buildSupportingAnnotation(IAnnotation jdtAnnotation) {
		return this.getAnnotationProvider().buildTypeSupportingAnnotation(this, jdtAnnotation);
	}
	
	@Override
	Annotation buildNullSupportingAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullTypeSupportingAnnotation(this, annotationName);
	}
	
	@Override
	ListIterator<String> validMappingAnnotationNames() {
		return this.getAnnotationProvider().typeMappingAnnotationNames();
	}
	
	@Override
	ListIterator<String> validSupportingAnnotationNames() {
		return this.getAnnotationProvider().typeSupportingAnnotationNames();
	}


	// ********** JavaResourcePersistentType implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	private void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return this.getMember().getElementName();
	}

	// ***** qualified name
	public String getQualifiedName() {
		return this.qualifiedName;
	}

	private void setQualifiedName(String qualifiedName) {
		String old = this.qualifiedName;
		this.qualifiedName = qualifiedName;
		this.firePropertyChanged(QUALIFIED_NAME_PROPERTY, old, qualifiedName);
	}

	private String buildQualifiedName() {
		return this.getMember().getFullyQualifiedName('.');  // no parameters are included here
	}

	// ***** superclass qualified name
	public String getSuperclassQualifiedName() {
		return this.superclassQualifiedName;
	}

	private void setSuperclassQualifiedName(String superclassQualifiedName) {
		String old = this.superclassQualifiedName;
		this.superclassQualifiedName = superclassQualifiedName;
		this.firePropertyChanged(SUPERCLASS_QUALIFIED_NAME_PROPERTY, old, superclassQualifiedName);
	}

	private String buildSuperclassQualifiedName() {
		return convertTypeSignatureToTypeName(this.getSuperclassTypeSignature());
	}

	private String getSuperclassTypeSignature() {
		try {
			return this.getMember().getSuperclassTypeSignature();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	// ***** abstract
	public boolean isAbstract() {
		return this.abstract_;
	}

	private void setAbstract(boolean abstract_) {
		boolean old = this.abstract_;
		this.abstract_ = abstract_;
		this.firePropertyChanged(ABSTRACT_PROPERTY, old, abstract_);
	}

	private boolean buildAbstract() {
		try {
			return Flags.isAbstract(this.getMember().getFlags());
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return false;
		}
	}

	// ***** access
	public AccessType getAccess() {
		return this.access;
	}

	private void setAccess(AccessType access) {
		AccessType old = this.access;
		this.access = access;
		this.firePropertyChanged(ACCESS_PROPERTY, old, access);
	}

	private AccessType buildAccess() {
		return JPTTools.buildAccess(this);
	}

	/**
	 * check only persistable attributes
	 */
	public boolean hasAnyAttributePersistenceAnnotations() {
		for (Iterator<JavaResourcePersistentAttribute> stream = this.persistableAttributes(); stream.hasNext(); ) {
			if (stream.next().hasAnyPersistenceAnnotations()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IType getMember() {
		return (IType) super.getMember();
	}


	// ********** fields **********

	public Iterator<JavaResourcePersistentAttribute> fields() {
		return this.getFields().iterator();
	}

	private Iterable<JavaResourcePersistentAttribute> getFields() {
		return new LiveCloneIterable<JavaResourcePersistentAttribute>(this.fields);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableFields() {
		return this.persistableMembers(this.fields());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistableFieldsWithSpecifiedFieldAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(this.persistableFields()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute resourceAttribute) {
				return resourceAttribute.getSpecifiedAccess() == AccessType.FIELD;
			}
		};
	}
	
	private void addField(JavaResourcePersistentAttribute field) {
		this.addItemToCollection(field, this.fields, FIELDS_COLLECTION);
	}

	private JavaResourcePersistentAttribute getField(String fieldName, int occurrence) {
		for (JavaResourcePersistentAttribute field : this.getFields()) {
			if (field.isFor(fieldName, occurrence)) {
				return field;
			}
		}
		return null;
	}

	private void removeFields(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.fields, FIELDS_COLLECTION);
	}

	private Vector<JavaResourcePersistentAttribute> buildFields() {
		IField[] jdtFields = this.getFields(this.getMember());
		Vector<JavaResourcePersistentAttribute> result = new Vector<JavaResourcePersistentAttribute>(jdtFields.length);
		for (IField jdtField : jdtFields) {
			result.add(this.buildField(jdtField));
		}
		return result;
	}

	private IField[] getFields(IType type) {
		try {
			return type.getFields();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_FIELD_ARRAY;
		}
	}
	private static final IField[] EMPTY_FIELD_ARRAY = new IField[0];

	private JavaResourcePersistentAttribute buildField(IField jdtField) {
		return new BinaryPersistentAttribute(this, jdtField);
	}


	// ********** methods **********

	public Iterator<JavaResourcePersistentAttribute> methods() {
		return this.getMethods().iterator();
	}

	private Iterable<JavaResourcePersistentAttribute> getMethods() {
		return new LiveCloneIterable<JavaResourcePersistentAttribute>(this.methods);
	}

	public Iterator<JavaResourcePersistentAttribute> persistableProperties() {
		return persistableMembers(this.methods());
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistablePropertiesWithSpecifiedPropertyAccess() {
		return new FilteringIterator<JavaResourcePersistentAttribute, JavaResourcePersistentAttribute>(this.persistableProperties()) {
			@Override
			protected boolean accept(JavaResourcePersistentAttribute resourceAttribute) {
				return resourceAttribute.getSpecifiedAccess() == AccessType.PROPERTY;
			}
		};
	}

	private JavaResourcePersistentAttribute getMethod(MethodSignature signature, int occurrence) {
		for (JavaResourcePersistentAttribute method : this.getMethods()) {
			if (method.isFor(signature, occurrence)) {
				return method;
			}
		}
		return null;
	}

	private void addMethod(JavaResourcePersistentAttribute method) {
		this.addItemToCollection(method, this.methods, METHODS_COLLECTION);
	}

	private void removeMethods(Collection<JavaResourcePersistentAttribute> remove) {
		this.removeItemsFromCollection(remove, this.methods, METHODS_COLLECTION);
	}

	private Vector<JavaResourcePersistentAttribute> buildMethods() {
		IMethod[] jdtMethods = this.getMethods(this.getMember());
		Vector<JavaResourcePersistentAttribute> result = new Vector<JavaResourcePersistentAttribute>(jdtMethods.length);
		for (IMethod jdtMethod : jdtMethods) {
			result.add(this.buildMethod(jdtMethod));
		}
		return result;
	}

	private IMethod[] getMethods(IType type) {
		try {
			return type.getMethods();
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
			return EMPTY_METHOD_ARRAY;
		}
	}
	private static final IMethod[] EMPTY_METHOD_ARRAY = new IMethod[0];

	private JavaResourcePersistentAttribute buildMethod(IMethod jdtMethod) {
		return new BinaryPersistentAttribute(this, jdtMethod);
	}


	// ********** attributes **********

	@SuppressWarnings("unchecked")
	public Iterator<JavaResourcePersistentAttribute> persistableAttributes() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableFields(),
				this.persistableProperties()
			);
	}
	
	public Iterator<JavaResourcePersistentAttribute> persistableAttributes(AccessType specifiedAccess) {
		if (specifiedAccess == null) {
			throw new IllegalArgumentException("specified access is null"); //$NON-NLS-1$
		}
		return (specifiedAccess == AccessType.FIELD) ?
					this.persistableAttributesForFieldAccessType() :
					this.persistableAttributesForPropertyAccessType();
	}
	
	@SuppressWarnings("unchecked")
	private Iterator<JavaResourcePersistentAttribute> persistableAttributesForFieldAccessType() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableFields(),
				this.persistablePropertiesWithSpecifiedPropertyAccess()
			);
	}
	
	@SuppressWarnings("unchecked")
	private Iterator<JavaResourcePersistentAttribute> persistableAttributesForPropertyAccessType() {
		return new CompositeIterator<JavaResourcePersistentAttribute>(
				this.persistableProperties(),
				this.persistableFieldsWithSpecifiedFieldAccess()
			);
	}
	

	// ********** IType adapter **********

	static class TypeAdapter implements Adapter {
		private final IType type;

		TypeAdapter(IType type) {
			super();
			this.type = type;
		}

		public IType getMember() {
			return this.type;
		}

		public boolean isPersistable() {
			return true;  // we only build a JAR type if it is "persistable"
		}

		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.type.getAnnotations();
		}

	}


	// ********** "persistable" check **********

	static boolean typeIsPersistable(IType type) {
		return (type != null)
				&& type.exists()
				&& JPTTools.typeIsPersistable(new JPTToolsAdapter(type));
	}


	// ********** JPT tools adapter **********

	/**
	 * JPTTools needs an adapter so it can work with either an IType
	 * or an ITypeBinding etc.
	 */
	static class JPTToolsAdapter implements JPTTools.TypeAdapter {
		private final IType type;

		protected JPTToolsAdapter(IType type) {
			super();
			if (type == null) {
				throw new NullPointerException();
			}
			this.type = type;
		}

		public int getModifiers() {
			try {
				return this.type.getFlags();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return 0;
			}
		}

		public boolean isAnnotation() {
			try {
				return this.type.isAnnotation();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isAnonymous() {
			try {
				return this.type.isAnonymous();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isArray() {
			return false;  // ???
		}

		public boolean isEnum() {
			try {
				return this.type.isEnum();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isInterface() {
			try {
				return this.type.isInterface();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isLocal() {
			try {
				return this.type.isLocal();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isMember() {
			try {
				return this.type.isMember();
			} catch (JavaModelException ex) {
				JptCorePlugin.log(ex);
				return false;
			}
		}

		public boolean isPrimitive() {
			return false;  // ???
		}
	
	}


	// ********** unsupported JavaResourcePersistentType implementation **********

	public Iterator<JavaResourcePersistentType> types() {
		throw new UnsupportedOperationException();
	}

	public Iterator<JavaResourcePersistentType> allTypes() {
		throw new UnsupportedOperationException();
	}

	public Iterator<JavaResourcePersistentType> persistableTypes() {
		throw new UnsupportedOperationException();
	}

}
