/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Discriminator Column</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlDiscriminatorColumn()
 * @model kind="class"
 * @generated
 */
public class XmlDiscriminatorColumn extends AbstractXmlDiscriminatorColumn
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final DiscriminatorType DISCRIMINATOR_TYPE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlDiscriminatorColumn()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.XML_DISCRIMINATOR_COLUMN;
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			Translator.END_TAG_NO_INDENT,
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildDiscrminiatorTypeTranslator(),
			buildColumnDefinitionTranslator(),
			buildLengthTranslator()
		};
	}

} // DiscriminatorColumn
