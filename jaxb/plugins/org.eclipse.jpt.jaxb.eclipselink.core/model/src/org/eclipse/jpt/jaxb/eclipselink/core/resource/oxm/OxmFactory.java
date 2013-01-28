/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage
 * @generated
 */
public class OxmFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OxmFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OxmFactory init()
	{
		try
		{
			OxmFactory theOxmFactory = (OxmFactory)EPackage.Registry.INSTANCE.getEFactory("jpt.eclipselink.oxm.xmi"); 
			if (theOxmFactory != null)
			{
				return theOxmFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OxmFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OxmFactory()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case OxmPackage.EJAVA_TYPE: return (EObject)createEJavaType();
			case OxmPackage.EXML_ACCESS_METHODS: return (EObject)createEXmlAccessMethods();
			case OxmPackage.EXML_ANY_ATTRIBUTE: return (EObject)createEXmlAnyAttribute();
			case OxmPackage.EXML_ANY_ELEMENT: return (EObject)createEXmlAnyElement();
			case OxmPackage.EXML_ATTRIBUTE: return (EObject)createEXmlAttribute();
			case OxmPackage.EXML_BINDINGS: return (EObject)createEXmlBindings();
			case OxmPackage.EXML_CLASS_EXTRACTOR: return (EObject)createEXmlClassExtractor();
			case OxmPackage.EXML_ELEMENT: return (EObject)createEXmlElement();
			case OxmPackage.EXML_ELEMENT_DECL: return (EObject)createEXmlElementDecl();
			case OxmPackage.EXML_ELEMENT_REF: return (EObject)createEXmlElementRef();
			case OxmPackage.EXML_ELEMENT_REFS: return (EObject)createEXmlElementRefs();
			case OxmPackage.EXML_ELEMENTS: return (EObject)createEXmlElements();
			case OxmPackage.EXML_ELEMENT_WRAPPER: return (EObject)createEXmlElementWrapper();
			case OxmPackage.EXML_ENUM: return (EObject)createEXmlEnum();
			case OxmPackage.EXML_ENUM_VALUE: return (EObject)createEXmlEnumValue();
			case OxmPackage.EXML_INVERSE_REFERENCE: return (EObject)createEXmlInverseReference();
			case OxmPackage.EXML_IS_SET_NULL_POLICY: return (EObject)createEXmlIsSetNullPolicy();
			case OxmPackage.EXML_IS_SET_PARAMETER: return (EObject)createEXmlIsSetParameter();
			case OxmPackage.EXML_JAVA_TYPE_ADAPTER: return (EObject)createEXmlJavaTypeAdapter();
			case OxmPackage.EXML_JOIN_NODE: return (EObject)createEXmlJoinNode();
			case OxmPackage.EXML_JOIN_NODES: return (EObject)createEXmlJoinNodes();
			case OxmPackage.EXML_MAP: return (EObject)createEXmlMap();
			case OxmPackage.EXML_NULL_POLICY: return (EObject)createEXmlNullPolicy();
			case OxmPackage.EXML_NS: return (EObject)createEXmlNs();
			case OxmPackage.EXML_PROPERTY: return (EObject)createEXmlProperty();
			case OxmPackage.EXML_READ_TRANSFORMER: return (EObject)createEXmlReadTransformer();
			case OxmPackage.EXML_REGISTRY: return (EObject)createEXmlRegistry();
			case OxmPackage.EXML_ROOT_ELEMENT: return (EObject)createEXmlRootElement();
			case OxmPackage.EXML_SCHEMA: return (EObject)createEXmlSchema();
			case OxmPackage.EXML_SCHEMA_TYPE: return (EObject)createEXmlSchemaType();
			case OxmPackage.EXML_SEE_ALSO: return (EObject)createEXmlSeeAlso();
			case OxmPackage.EXML_TRANSFORMATION: return (EObject)createEXmlTransformation();
			case OxmPackage.EXML_TRANSIENT: return (EObject)createEXmlTransient();
			case OxmPackage.EXML_TYPE: return (EObject)createEXmlType();
			case OxmPackage.EXML_VALUE: return (EObject)createEXmlValue();
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS: return (EObject)createEXmlVirtualAccessMethods();
			case OxmPackage.EXML_WRITE_TRANSFORMER: return (EObject)createEXmlWriteTransformer();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue)
	{
		switch (eDataType.getClassifierID())
		{
			case OxmPackage.EXML_ACCESS_ORDER:
				return createEXmlAccessOrderFromString(eDataType, initialValue);
			case OxmPackage.EXML_ACCESS_TYPE:
				return createEXmlAccessTypeFromString(eDataType, initialValue);
			case OxmPackage.EXML_MARSHAL_NULL_REPRESENTATION:
				return createEXmlMarshalNullRepresentationFromString(eDataType, initialValue);
			case OxmPackage.EXML_NS_FORM:
				return createEXmlNsFormFromString(eDataType, initialValue);
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS_SCHEMA:
				return createEXmlVirtualAccessMethodsSchemaFromString(eDataType, initialValue);
			case OxmPackage.EPROP_ORDER:
				return createEPropOrderFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue)
	{
		switch (eDataType.getClassifierID())
		{
			case OxmPackage.EXML_ACCESS_ORDER:
				return convertEXmlAccessOrderToString(eDataType, instanceValue);
			case OxmPackage.EXML_ACCESS_TYPE:
				return convertEXmlAccessTypeToString(eDataType, instanceValue);
			case OxmPackage.EXML_MARSHAL_NULL_REPRESENTATION:
				return convertEXmlMarshalNullRepresentationToString(eDataType, instanceValue);
			case OxmPackage.EXML_NS_FORM:
				return convertEXmlNsFormToString(eDataType, instanceValue);
			case OxmPackage.EXML_VIRTUAL_ACCESS_METHODS_SCHEMA:
				return convertEXmlVirtualAccessMethodsSchemaToString(eDataType, instanceValue);
			case OxmPackage.EPROP_ORDER:
				return convertEPropOrderToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EJavaType createEJavaType()
	{
		EJavaType eJavaType = new EJavaType();
		return eJavaType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlAccessMethods createEXmlAccessMethods()
	{
		EXmlAccessMethods eXmlAccessMethods = new EXmlAccessMethods();
		return eXmlAccessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlAnyAttribute createEXmlAnyAttribute()
	{
		EXmlAnyAttribute eXmlAnyAttribute = new EXmlAnyAttribute();
		return eXmlAnyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlAnyElement createEXmlAnyElement()
	{
		EXmlAnyElement eXmlAnyElement = new EXmlAnyElement();
		return eXmlAnyElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlAttribute createEXmlAttribute()
	{
		EXmlAttribute eXmlAttribute = new EXmlAttribute();
		return eXmlAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlBindings createEXmlBindings()
	{
		EXmlBindings eXmlBindings = new EXmlBindings();
		return eXmlBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlClassExtractor createEXmlClassExtractor()
	{
		EXmlClassExtractor eXmlClassExtractor = new EXmlClassExtractor();
		return eXmlClassExtractor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlElement createEXmlElement()
	{
		EXmlElement eXmlElement = new EXmlElement();
		return eXmlElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlElementDecl createEXmlElementDecl()
	{
		EXmlElementDecl eXmlElementDecl = new EXmlElementDecl();
		return eXmlElementDecl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlElementRef createEXmlElementRef()
	{
		EXmlElementRef eXmlElementRef = new EXmlElementRef();
		return eXmlElementRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlElementRefs createEXmlElementRefs()
	{
		EXmlElementRefs eXmlElementRefs = new EXmlElementRefs();
		return eXmlElementRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlElements createEXmlElements()
	{
		EXmlElements eXmlElements = new EXmlElements();
		return eXmlElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlElementWrapper createEXmlElementWrapper()
	{
		EXmlElementWrapper eXmlElementWrapper = new EXmlElementWrapper();
		return eXmlElementWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlEnum createEXmlEnum()
	{
		EXmlEnum eXmlEnum = new EXmlEnum();
		return eXmlEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlEnumValue createEXmlEnumValue()
	{
		EXmlEnumValue eXmlEnumValue = new EXmlEnumValue();
		return eXmlEnumValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlInverseReference createEXmlInverseReference()
	{
		EXmlInverseReference eXmlInverseReference = new EXmlInverseReference();
		return eXmlInverseReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlIsSetNullPolicy createEXmlIsSetNullPolicy()
	{
		EXmlIsSetNullPolicy eXmlIsSetNullPolicy = new EXmlIsSetNullPolicy();
		return eXmlIsSetNullPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlIsSetParameter createEXmlIsSetParameter()
	{
		EXmlIsSetParameter eXmlIsSetParameter = new EXmlIsSetParameter();
		return eXmlIsSetParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlJavaTypeAdapter createEXmlJavaTypeAdapter()
	{
		EXmlJavaTypeAdapter eXmlJavaTypeAdapter = new EXmlJavaTypeAdapter();
		return eXmlJavaTypeAdapter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlJoinNode createEXmlJoinNode()
	{
		EXmlJoinNode eXmlJoinNode = new EXmlJoinNode();
		return eXmlJoinNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlJoinNodes createEXmlJoinNodes()
	{
		EXmlJoinNodes eXmlJoinNodes = new EXmlJoinNodes();
		return eXmlJoinNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlMap createEXmlMap()
	{
		EXmlMap eXmlMap = new EXmlMap();
		return eXmlMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlNullPolicy createEXmlNullPolicy()
	{
		EXmlNullPolicy eXmlNullPolicy = new EXmlNullPolicy();
		return eXmlNullPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlNs createEXmlNs()
	{
		EXmlNs eXmlNs = new EXmlNs();
		return eXmlNs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlProperty createEXmlProperty()
	{
		EXmlProperty eXmlProperty = new EXmlProperty();
		return eXmlProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlReadTransformer createEXmlReadTransformer()
	{
		EXmlReadTransformer eXmlReadTransformer = new EXmlReadTransformer();
		return eXmlReadTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlRegistry createEXmlRegistry()
	{
		EXmlRegistry eXmlRegistry = new EXmlRegistry();
		return eXmlRegistry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlRootElement createEXmlRootElement()
	{
		EXmlRootElement eXmlRootElement = new EXmlRootElement();
		return eXmlRootElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlSchema createEXmlSchema()
	{
		EXmlSchema eXmlSchema = new EXmlSchema();
		return eXmlSchema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlSchemaType createEXmlSchemaType()
	{
		EXmlSchemaType eXmlSchemaType = new EXmlSchemaType();
		return eXmlSchemaType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlSeeAlso createEXmlSeeAlso()
	{
		EXmlSeeAlso eXmlSeeAlso = new EXmlSeeAlso();
		return eXmlSeeAlso;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlTransformation createEXmlTransformation()
	{
		EXmlTransformation eXmlTransformation = new EXmlTransformation();
		return eXmlTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlTransient createEXmlTransient()
	{
		EXmlTransient eXmlTransient = new EXmlTransient();
		return eXmlTransient;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlType createEXmlType()
	{
		EXmlType eXmlType = new EXmlType();
		return eXmlType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlValue createEXmlValue()
	{
		EXmlValue eXmlValue = new EXmlValue();
		return eXmlValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlVirtualAccessMethods createEXmlVirtualAccessMethods()
	{
		EXmlVirtualAccessMethods eXmlVirtualAccessMethods = new EXmlVirtualAccessMethods();
		return eXmlVirtualAccessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlWriteTransformer createEXmlWriteTransformer()
	{
		EXmlWriteTransformer eXmlWriteTransformer = new EXmlWriteTransformer();
		return eXmlWriteTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlAccessOrder createEXmlAccessOrderFromString(EDataType eDataType, String initialValue)
	{
		EXmlAccessOrder result = EXmlAccessOrder.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEXmlAccessOrderToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlAccessType createEXmlAccessTypeFromString(EDataType eDataType, String initialValue)
	{
		EXmlAccessType result = EXmlAccessType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEXmlAccessTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlMarshalNullRepresentation createEXmlMarshalNullRepresentationFromString(EDataType eDataType, String initialValue)
	{
		EXmlMarshalNullRepresentation result = EXmlMarshalNullRepresentation.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEXmlMarshalNullRepresentationToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlNsForm createEXmlNsFormFromString(EDataType eDataType, String initialValue)
	{
		EXmlNsForm result = EXmlNsForm.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEXmlNsFormToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EXmlVirtualAccessMethodsSchema createEXmlVirtualAccessMethodsSchemaFromString(EDataType eDataType, String initialValue)
	{
		EXmlVirtualAccessMethodsSchema result = EXmlVirtualAccessMethodsSchema.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEXmlVirtualAccessMethodsSchemaToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> createEPropOrderFromString(EDataType eDataType, String initialValue)
	{
		if (initialValue == null) return null;
		List<String> result = new ArrayList<String>();
		for (String item : split(initialValue))
		{
			result.add((String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, item));
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEPropOrderToString(EDataType eDataType, Object instanceValue)
	{
		if (instanceValue == null) return null;
		List<?> list = (List<?>)instanceValue;
		if (list.isEmpty()) return "";
		StringBuffer result = new StringBuffer();
		for (Object item : list)
		{
			result.append(XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, item));
			result.append(' ');
		}
		return result.substring(0, result.length() - 1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OxmPackage getOxmPackage()
	{
		return (OxmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OxmPackage getPackage()
	{
		return OxmPackage.eINSTANCE;
	}

} //OxmFactory
