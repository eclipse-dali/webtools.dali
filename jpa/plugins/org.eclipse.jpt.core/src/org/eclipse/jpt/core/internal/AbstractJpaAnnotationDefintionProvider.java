/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

public abstract class AbstractJpaAnnotationDefintionProvider
		implements JpaAnnotationDefinitionProvider
{
	private AnnotationDefinition[] typeAnnotationDefinitions;

	private AnnotationDefinition[] typeMappingAnnotationDefinitions;

	private AnnotationDefinition[] attributeAnnotationDefinitions;


	protected AbstractJpaAnnotationDefintionProvider() {
		super();
	}

	// ********** type annotation definitions **********

	public synchronized Iterator<AnnotationDefinition> typeAnnotationDefinitions() {
		if (this.typeAnnotationDefinitions == null) {
			this.typeAnnotationDefinitions = this.buildTypeAnnotationDefinitions();
		}
		return new ArrayIterator<AnnotationDefinition>(this.typeAnnotationDefinitions);
	}

	protected AnnotationDefinition[] buildTypeAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}

	/**
	 * Subclasses must override this to specify type annotation definitions.
	 */
	protected void addTypeAnnotationDefinitionsTo(@SuppressWarnings("unused") List<AnnotationDefinition> definitions) {
		// no op
	}

	// ********** type mapping annotation definitions **********

	public synchronized Iterator<AnnotationDefinition> typeMappingAnnotationDefinitions() {
		if (this.typeMappingAnnotationDefinitions == null) {
			this.typeMappingAnnotationDefinitions = this.buildTypeMappingAnnotationDefinitions();
		}
		return new ArrayIterator<AnnotationDefinition>(this.typeMappingAnnotationDefinitions);
	}

	protected AnnotationDefinition[] buildTypeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeMappingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}

	/**
	 * Subclasses must override this to specify type mapping annotation
	 * definitions.
	 */
	protected void addTypeMappingAnnotationDefinitionsTo(@SuppressWarnings("unused") List<AnnotationDefinition> definitions) {
		// no op
	}

	// ********** attribute annotation definitions **********

	public synchronized Iterator<AnnotationDefinition> attributeAnnotationDefinitions() {
		if (this.attributeAnnotationDefinitions == null) {
			this.attributeAnnotationDefinitions = this.buildAttributeAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.attributeAnnotationDefinitions);
	}

	protected AnnotationDefinition[] buildAttributeAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}

	/**
	 * Subclasses must override this to specify attribute annotation
	 * definitions.
	 */
	protected void addAttributeAnnotationDefinitionsTo(@SuppressWarnings("unused") List<AnnotationDefinition> definitions) {
		// no op
	}
}
