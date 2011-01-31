/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.translators;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorPath;

/**
 * Translator that contains a list of child translators and no special
 * behavior.
 */
public class SimpleTranslator
	extends Translator
{
	protected Translator[] children;


	// ********** constructors **********

	public SimpleTranslator(String domPathAndNames, EClass eClass) {
		super(domPathAndNames, eClass);
	}

	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature) {
		super(domPathAndNames, eStructuralFeature);
	}

	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature, Translator[] children) {
		super(domPathAndNames, eStructuralFeature);
		this.children = children;
	}

	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature, EClass eClass) {
		super(domPathAndNames, eStructuralFeature, eClass);
	}
	
	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature, EClass eClass, Translator[] children) {
		super(domPathAndNames, eStructuralFeature, eClass);
		this.children = children;
	}

	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature, TranslatorPath translatorPath) {
		super(domPathAndNames, eStructuralFeature, translatorPath);
	}

	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature, TranslatorPath[] translatorPaths) {
		super(domPathAndNames, eStructuralFeature, translatorPaths);
	}

	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature, int style) {
		super(domPathAndNames, eStructuralFeature, style);
	}
	
	public SimpleTranslator(String domPathAndNames, EStructuralFeature aFeature, int style, EClass eClass, Translator[] children) {
		this(domPathAndNames, aFeature, style);
		setEMFClass(eClass);
		this.children = children;
	}

	public SimpleTranslator(String domPathAndNames, EStructuralFeature eStructuralFeature, int style, Translator[] children) {
		super(domPathAndNames, eStructuralFeature, style);
		this.children = children;
	}


	// ********** children **********

	/**
	 * Widen method access to 'public'.
	 */
	@Override
	public Translator[] getChildren() {
		return this.children;
	}

	protected Translator[] getChildren_() {
		return (this.children == null) ? EMPTY_TRANSLATOR_ARRAY : this.children;
	}
	protected static final Translator[] EMPTY_TRANSLATOR_ARRAY = new Translator[0];

	/**
	 * Set the translator's children.
	 * Return the translator.
	 */
	public void setChildren(Translator[] children) {
		this.children = children;
	}

	/**
	 * Add the specified translator to the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleTranslator addChild(Translator translator) {
		this.children = ArrayTools.add(this.getChildren_(), translator);
		return this;
	}

	/**
	 * Add the specified translators to the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleTranslator addChildren(Translator[] translators) {
		this.children = ArrayTools.addAll(this.getChildren_(), translators);
		return this;
	}

	/**
	 * Remove the specified translator from the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleTranslator removeChild(Translator translator) {
		this.children = ArrayTools.remove(this.children, translator);
		return this;
	}

	/**
	 * Remove the specified translators from the translator's list of children.
	 * Return the translator for method chaining.
	 */
	public SimpleTranslator removeChildren(Translator[] translators) {
		this.children = ArrayTools.removeAll(this.children, (Object[]) translators);
		return this;
	}

}
