/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.jaxb.core.AnnotationDefinitionProvider;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

public abstract class AbstractAnnotationDefinitionProvider
		implements AnnotationDefinitionProvider
{
	private AnnotationDefinition[] typeAnnotationDefinitions;

	private AnnotationDefinition[] typeMappingAnnotationDefinitions;

	private AnnotationDefinition[] attributeAnnotationDefinitions;

	private AnnotationDefinition[] packageAnnotationDefinitions;


	protected AbstractAnnotationDefinitionProvider() {
		super();
	}

	// ********** type annotation definitions **********

	public synchronized Iterable<AnnotationDefinition> getTypeAnnotationDefinitions() {
		if (this.typeAnnotationDefinitions == null) {
			this.typeAnnotationDefinitions = this.buildTypeAnnotationDefinitions();
		}
		return new ArrayIterable<AnnotationDefinition>(this.typeAnnotationDefinitions);
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

	public synchronized Iterable<AnnotationDefinition> getTypeMappingAnnotationDefinitions() {
		if (this.typeMappingAnnotationDefinitions == null) {
			this.typeMappingAnnotationDefinitions = this.buildTypeMappingAnnotationDefinitions();
		}
		return new ArrayIterable<AnnotationDefinition>(this.typeMappingAnnotationDefinitions);
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

	public synchronized Iterable<AnnotationDefinition> getAttributeAnnotationDefinitions() {
		if (this.attributeAnnotationDefinitions == null) {
			this.attributeAnnotationDefinitions = this.buildAttributeAnnotationDefinitions();
		}
		return new ArrayListIterable<AnnotationDefinition>(this.attributeAnnotationDefinitions);
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


	// ********** package annotation definitions **********

	public synchronized ListIterable<AnnotationDefinition> getPackageAnnotationDefinitions() {
		if (this.packageAnnotationDefinitions == null) {
			this.packageAnnotationDefinitions = this.buildPackageAnnotationDefinitions();
		}
		return new ArrayListIterable<AnnotationDefinition>(this.packageAnnotationDefinitions);
	}

	protected AnnotationDefinition[] buildPackageAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addPackageAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}

	/**
	 * Subclasses must override this to specify package annotation
	 * definitions. No package annotation definitions by default.
	 */
	protected void addPackageAnnotationDefinitionsTo(@SuppressWarnings("unused") List<AnnotationDefinition> definitions) {
		// no op
	}
}
