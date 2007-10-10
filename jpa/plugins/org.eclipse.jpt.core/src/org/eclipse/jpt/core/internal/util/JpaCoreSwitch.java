/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.*;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaDataSource;
import org.eclipse.jpt.core.internal.IJpaEObject;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaModel;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.IXmlEObject;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.JpaDataSource;
import org.eclipse.jpt.core.internal.JpaEObject;
import org.eclipse.jpt.core.internal.JpaFile;
import org.eclipse.jpt.core.internal.JpaModel;
import org.eclipse.jpt.core.internal.JpaProject;
import org.eclipse.jpt.core.internal.NullTypeMapping;
import org.eclipse.jpt.core.internal.XmlEObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.JpaCorePackage
 * @generated
 */
public class JpaCoreSwitch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static JpaCorePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaCoreSwitch() {
		if (modelPackage == null) {
			modelPackage = JpaCorePackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case JpaCorePackage.IJPA_EOBJECT : {
				IJpaEObject iJpaEObject = (IJpaEObject) theEObject;
				T result = caseIJpaEObject(iJpaEObject);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.JPA_EOBJECT : {
				JpaEObject jpaEObject = (JpaEObject) theEObject;
				T result = caseJpaEObject(jpaEObject);
				if (result == null)
					result = caseIJpaEObject(jpaEObject);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IJPA_DATA_SOURCE : {
				IJpaDataSource iJpaDataSource = (IJpaDataSource) theEObject;
				T result = caseIJpaDataSource(iJpaDataSource);
				if (result == null)
					result = caseIJpaEObject(iJpaDataSource);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.JPA_DATA_SOURCE : {
				JpaDataSource jpaDataSource = (JpaDataSource) theEObject;
				T result = caseJpaDataSource(jpaDataSource);
				if (result == null)
					result = caseJpaEObject(jpaDataSource);
				if (result == null)
					result = caseIJpaDataSource(jpaDataSource);
				if (result == null)
					result = caseIJpaEObject(jpaDataSource);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IJPA_FILE : {
				IJpaFile iJpaFile = (IJpaFile) theEObject;
				T result = caseIJpaFile(iJpaFile);
				if (result == null)
					result = caseIJpaEObject(iJpaFile);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.JPA_FILE : {
				JpaFile jpaFile = (JpaFile) theEObject;
				T result = caseJpaFile(jpaFile);
				if (result == null)
					result = caseJpaEObject(jpaFile);
				if (result == null)
					result = caseIJpaFile(jpaFile);
				if (result == null)
					result = caseIJpaEObject(jpaFile);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IJPA_SOURCE_OBJECT : {
				IJpaSourceObject iJpaSourceObject = (IJpaSourceObject) theEObject;
				T result = caseIJpaSourceObject(iJpaSourceObject);
				if (result == null)
					result = caseIJpaEObject(iJpaSourceObject);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IXML_EOBJECT : {
				IXmlEObject iXmlEObject = (IXmlEObject) theEObject;
				T result = caseIXmlEObject(iXmlEObject);
				if (result == null)
					result = caseIJpaEObject(iXmlEObject);
				if (result == null)
					result = caseIJpaSourceObject(iXmlEObject);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.XML_EOBJECT : {
				XmlEObject xmlEObject = (XmlEObject) theEObject;
				T result = caseXmlEObject(xmlEObject);
				if (result == null)
					result = caseJpaEObject(xmlEObject);
				if (result == null)
					result = caseIXmlEObject(xmlEObject);
				if (result == null)
					result = caseIJpaEObject(xmlEObject);
				if (result == null)
					result = caseIJpaSourceObject(xmlEObject);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IJPA_CONTENT_NODE : {
				IJpaContentNode iJpaContentNode = (IJpaContentNode) theEObject;
				T result = caseIJpaContentNode(iJpaContentNode);
				if (result == null)
					result = caseIJpaSourceObject(iJpaContentNode);
				if (result == null)
					result = caseIJpaEObject(iJpaContentNode);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IJPA_ROOT_CONTENT_NODE : {
				IJpaRootContentNode iJpaRootContentNode = (IJpaRootContentNode) theEObject;
				T result = caseIJpaRootContentNode(iJpaRootContentNode);
				if (result == null)
					result = caseIJpaContentNode(iJpaRootContentNode);
				if (result == null)
					result = caseIJpaSourceObject(iJpaRootContentNode);
				if (result == null)
					result = caseIJpaEObject(iJpaRootContentNode);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IPERSISTENT_TYPE : {
				IPersistentType iPersistentType = (IPersistentType) theEObject;
				T result = caseIPersistentType(iPersistentType);
				if (result == null)
					result = caseIJpaContentNode(iPersistentType);
				if (result == null)
					result = caseIJpaSourceObject(iPersistentType);
				if (result == null)
					result = caseIJpaEObject(iPersistentType);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.ITYPE_MAPPING : {
				ITypeMapping iTypeMapping = (ITypeMapping) theEObject;
				T result = caseITypeMapping(iTypeMapping);
				if (result == null)
					result = caseIJpaSourceObject(iTypeMapping);
				if (result == null)
					result = caseIJpaEObject(iTypeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.NULL_TYPE_MAPPING : {
				NullTypeMapping nullTypeMapping = (NullTypeMapping) theEObject;
				T result = caseNullTypeMapping(nullTypeMapping);
				if (result == null)
					result = caseJpaEObject(nullTypeMapping);
				if (result == null)
					result = caseITypeMapping(nullTypeMapping);
				if (result == null)
					result = caseIJpaSourceObject(nullTypeMapping);
				if (result == null)
					result = caseIJpaEObject(nullTypeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IPERSISTENT_ATTRIBUTE : {
				IPersistentAttribute iPersistentAttribute = (IPersistentAttribute) theEObject;
				T result = caseIPersistentAttribute(iPersistentAttribute);
				if (result == null)
					result = caseIJpaContentNode(iPersistentAttribute);
				if (result == null)
					result = caseIJpaSourceObject(iPersistentAttribute);
				if (result == null)
					result = caseIJpaEObject(iPersistentAttribute);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCorePackage.IATTRIBUTE_MAPPING : {
				IAttributeMapping iAttributeMapping = (IAttributeMapping) theEObject;
				T result = caseIAttributeMapping(iAttributeMapping);
				if (result == null)
					result = caseIJpaSourceObject(iAttributeMapping);
				if (result == null)
					result = caseIJpaEObject(iAttributeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default :
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaEObject(IJpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Jpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Jpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaEObject(JpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaFile(IJpaFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Jpa File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Jpa File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaFile(JpaFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Source Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Source Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaSourceObject(IJpaSourceObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IXml EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IXml EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIXmlEObject(IXmlEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaContentNode(IJpaContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaRootContentNode(IJpaRootContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPersistent Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPersistent Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPersistentType(IPersistentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IType Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IType Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITypeMapping(ITypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Null Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Null Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNullTypeMapping(NullTypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPersistent Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPersistent Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPersistentAttribute(IPersistentAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAttributeMapping(IAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEObject(XmlEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Data Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Data Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaDataSource(IJpaDataSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Jpa Data Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Jpa Data Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaDataSource(JpaDataSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}
} //JpaCoreSwitch
