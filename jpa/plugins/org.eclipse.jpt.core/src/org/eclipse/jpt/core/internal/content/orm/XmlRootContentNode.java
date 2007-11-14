/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.JpaFile;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmArtifactEdit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Root Content Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode#getEntityMappings <em>Entity Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlRootContentNode()
 * @model kind="class"
 * @generated
 */
public class XmlRootContentNode extends XmlEObject
	implements IJpaRootContentNode
{
	/**
	 * The cached value of the '{@link #getEntityMappings() <em>Entity Mappings</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityMappings()
	 * @generated
	 * @ordered
	 */
	protected EntityMappingsInternal entityMappings;
	
	private OrmArtifactEdit artifactEdit;
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlRootContentNode() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_ROOT_CONTENT_NODE;
	}

	/* @see IJpaContentNode#getId() */
	public Object getId() {
		return IXmlContentNodes.XML_ROOT_ID;
	}

	@Override
	public IJpaRootContentNode getRoot() {
		return this;
	}
	
	/**
	 * Returns the value of the '<em><b>Jpa File</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.JpaFile#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jpa File</em>' container reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIJpaRootContentNode_JpaFile()
	 * @see org.eclipse.jpt.core.internal.JpaFile#getContent
	 * @model opposite="content" transient="false" changeable="false"
	 * @generated
	 */
	public IJpaFile getJpaFile() {
		if (eContainerFeatureID != OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE)
			return null;
		return (IJpaFile) eContainer();
	}

	public IResource getResource() {
		return getJpaFile().getResource();
	}

	/**
	 * Returns the value of the '<em><b>Entity Mappings</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Mappings</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Mappings</em>' reference.
	 * @see #setEntityMappings(EntityMappingsInternal)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlRootContentNode_EntityMappings()
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getRoot
	 * @model opposite="root" resolveProxies="false" required="true" ordered="false"
	 * @generated
	 */
	public EntityMappingsInternal getEntityMappings() {
		return entityMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntityMappings(EntityMappingsInternal newEntityMappings, NotificationChain msgs) {
		EntityMappingsInternal oldEntityMappings = entityMappings;
		entityMappings = newEntityMappings;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS, oldEntityMappings, newEntityMappings);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode#getEntityMappings <em>Entity Mappings</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Mappings</em>' reference.
	 * @see #getEntityMappings()
	 * @generated
	 */
	public void setEntityMappings(EntityMappingsInternal newEntityMappings) {
		if (newEntityMappings != entityMappings) {
			NotificationChain msgs = null;
			if (entityMappings != null)
				msgs = ((InternalEObject) entityMappings).eInverseRemove(this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT, EntityMappingsInternal.class, msgs);
			if (newEntityMappings != null)
				msgs = ((InternalEObject) newEntityMappings).eInverseAdd(this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT, EntityMappingsInternal.class, msgs);
			msgs = basicSetEntityMappings(newEntityMappings, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS, newEntityMappings, newEntityMappings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE :
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE, msgs);
			case OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS :
				if (entityMappings != null)
					msgs = ((InternalEObject) entityMappings).eInverseRemove(this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT, EntityMappingsInternal.class, msgs);
				return basicSetEntityMappings((EntityMappingsInternal) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE :
				return eBasicSetContainer(null, OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE, msgs);
			case OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS :
				return basicSetEntityMappings(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE :
				return eInternalContainer().eInverseRemove(this, JpaCorePackage.JPA_FILE__CONTENT, JpaFile.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE :
				return getJpaFile();
			case OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS :
				return getEntityMappings();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS :
				setEntityMappings((EntityMappingsInternal) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS :
				setEntityMappings((EntityMappingsInternal) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE :
				return getJpaFile() != null;
			case OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS :
				return entityMappings != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IJpaContentNode.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJpaRootContentNode.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE :
					return JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE;
				default :
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IJpaContentNode.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJpaRootContentNode.class) {
			switch (baseFeatureID) {
				case JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE :
					return OrmPackage.XML_ROOT_CONTENT_NODE__JPA_FILE;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public void dispose() {
		if (artifactEdit != null) {
			artifactEdit.dispose();
		}
	}
	
	@Override
	public ITextRange fullTextRange() {
		return ITextRange.Empty.instance();
	}
	
	@Override
	//need to return null here for TextEditorSelectionParticipant.selectionChanged().
	//It will not do a select in the tree if the textRange is null.
	//we should probably instead have a ITextRange.isEmpty() call
	public ITextRange selectionTextRange() {
		return null;
	}
	
	/* (non-Javadoc)
	 * 
	 * @see IJpaRootContentNode#getContentNode(int)
	 */
	public IJpaContentNode getContentNode(int offset) {
		if (getEntityMappings() == null || !getEntityMappings().getNode().contains(offset)) {
			return this;
		}
		return getEntityMappings().getContentNode(offset);
	}

	/* (non-Javadoc)
	 * 
	 * @see IJpaRootContentNode#handleJavaElementChangedEvent(ElementChangedEvent)
	 */
	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
		if (getEntityMappings() != null) {
			getEntityMappings().handleJavaElementChangedEvent(event);
		}
	}

	public void setArtifactEdit(OrmArtifactEdit ormArtifactEdit) {
		artifactEdit = ormArtifactEdit;
	}
}
