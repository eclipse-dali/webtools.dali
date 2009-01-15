/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

/**
 * 
 */
public abstract class AbstractJpaAnnotationDefintionProvider
	implements JpaAnnotationDefinitionProvider
{
	/**
	 * Ordered list of possible type mapping annotations. Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private AnnotationDefinition[] typeMappingAnnotationDefinitions;
	
	private AnnotationDefinition[] typeSupportingAnnotationDefinitions;
	
	/**
	 * Ordered list of possible attribute mapping annotations. Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private AnnotationDefinition[] attributeMappingAnnotationDefinitions;
	
	private AnnotationDefinition[] attributeSupportingAnnotationDefinitions;
	
	protected AbstractJpaAnnotationDefintionProvider() {
		super();
	}
	
	// ********** type annotation definitions **********

	public synchronized ListIterator<AnnotationDefinition> typeMappingAnnotationDefinitions() {
		if (this.typeMappingAnnotationDefinitions == null) {
			this.typeMappingAnnotationDefinitions = this.buildTypeMappingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.typeMappingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildTypeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeMappingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify type mapping annotation definitions.
	 */
	protected abstract void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	
	public synchronized ListIterator<AnnotationDefinition> typeSupportingAnnotationDefinitions() {
		if (this.typeSupportingAnnotationDefinitions == null) {
			this.typeSupportingAnnotationDefinitions = this.buildTypeSupportingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.typeSupportingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildTypeSupportingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addTypeSupportingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify type supporting annotation definitions.
	 */
	protected abstract void addTypeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	

	// ********** attribute annotation definitions **********

	public synchronized ListIterator<AnnotationDefinition> attributeMappingAnnotationDefinitions() {
		if (this.attributeMappingAnnotationDefinitions == null) {
			this.attributeMappingAnnotationDefinitions = this.buildAttributeMappingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.attributeMappingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildAttributeMappingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeMappingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify  attribute mapping annotation definitions.
	 */
	protected abstract void addAttributeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	
	public synchronized ListIterator<AnnotationDefinition> attributeSupportingAnnotationDefinitions() {
		if (this.attributeSupportingAnnotationDefinitions == null) {
			this.attributeSupportingAnnotationDefinitions = this.buildAttributeSupportingAnnotationDefinitions();
		}
		return new ArrayListIterator<AnnotationDefinition>(this.attributeSupportingAnnotationDefinitions);
	}
	
	protected AnnotationDefinition[] buildAttributeSupportingAnnotationDefinitions() {
		ArrayList<AnnotationDefinition> definitions = new ArrayList<AnnotationDefinition>();
		this.addAttributeSupportingAnnotationDefinitionsTo(definitions);
		return definitions.toArray(new AnnotationDefinition[definitions.size()]);
	}
	
	/**
	 * Subclasses must override this to specify attribute supporting annotation definitions.
	 */
	protected abstract void addAttributeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions);
	
}
