/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.source;

import java.util.Arrays;
import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceQueryAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceQueryHintAnnotation;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameterAnnotation2_1;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

/**
 * <code>javax.persistence.NamedStoredProcedureQuery</code>
 */
public final class SourceNamedStoredProcedureQuery2_1Annotation
	extends SourceQueryAnnotation
	implements NamedStoredProcedureQueryAnnotation2_1
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES);

	DeclarationAnnotationElementAdapter<String> procedureNameDeclarationAdapter;
	AnnotationElementAdapter<String> procedureNameAdapter;
	String procedureName;
	TextRange procedureNameTextRange;
	
	final StoredProcedureParameterAnnotationContainer storedProcedureParametersContainer = new StoredProcedureParameterAnnotationContainer();
	
	private DeclarationAnnotationElementAdapter<String[]> resultClassesDeclarationAdapter;
	private AnnotationElementAdapter<String[]> resultClassesAdapter;
	private final Vector<String> resultClasses = new Vector<String>();
	private TextRange resultClassesTextRange;
	
	/**
	 * TODO: Need to handle fullyQualifiedResultClassName/fqResultClassNameStale for each of the result classes
	    private final Vector<String> fullyQualifiedResultClassNames = new Vector<String>();
		// we need a flag since the f-q name can be null
		private boolean fqResultClassNameStale = true;
		
		I think you can model this after what Paul recently did in 
		org.eclipse.jpt.jaxb.core.internal.resource.java.source.SourceXmlSeeAlsoAnnotation
	 */

	private DeclarationAnnotationElementAdapter<String[]> resultSetMappingsDeclarationAdapter;
	private AnnotationElementAdapter<String[]> resultSetMappingsAdapter;
	private final Vector<String> resultSetMappings = new Vector<String>();
	private TextRange resultSetMappingsTextRange;

	public static SourceNamedStoredProcedureQuery2_1Annotation buildSourceNamedStoredProcedureQuery2_1Annotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement element, 
			int index)
	{
		IndexedDeclarationAnnotationAdapter idaa = buildNamedStoredProcedureQuery2_1DeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildNamedStoredProcedureQuery2_1AnnotationAdapter(element, idaa);
		return new SourceNamedStoredProcedureQuery2_1Annotation(
			parent,
			element,
			idaa,
			iaa);
	}

	private SourceNamedStoredProcedureQuery2_1Annotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter)
	{
		super(parent, element, daa, annotationAdapter);
		this.procedureNameDeclarationAdapter = this.buildProcedureNameDeclarationAdapter();
		this.procedureNameAdapter = this.buildProcedureNameAdapter();
		this.resultClassesDeclarationAdapter = this.buildResultClassesDeclarationAdapter();
		this.resultClassesAdapter = this.buildResultClassesAdapter();
		this.resultSetMappingsDeclarationAdapter = this.buildResultSetMappingsDeclarationAdapter();
		this.resultSetMappingsAdapter = this.buildResultSetMappingsAdapter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		
		this.procedureName = this.buildProcedureName(astAnnotation);
		this.procedureNameTextRange = this.buildProcedureNameTextRange(astAnnotation);

		this.storedProcedureParametersContainer.initializeFromContainerAnnotation(astAnnotation);
		
		this.initializeResultClasses(astAnnotation);
		this.resultClassesTextRange = this.buildResultClassesTextRange(astAnnotation);

		this.initializeResultSetMappings(astAnnotation);
		this.resultSetMappingsTextRange = this.buildResultSetMappingsTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		
		this.syncProcedureName(this.buildProcedureName(astAnnotation));
		this.procedureNameTextRange = this.buildProcedureNameTextRange(astAnnotation);

		this.storedProcedureParametersContainer.synchronize(astAnnotation);
		
		this.syncResultClasses(astAnnotation);
		this.resultClassesTextRange = this.buildResultClassesTextRange(astAnnotation);
		
		this.syncResultSetMappings(astAnnotation);
		this.resultSetMappingsTextRange = this.buildResultSetMappingsTextRange(astAnnotation);
	
	}

	// ********** AbstractNamedStoredProcedureQuery2_1Annotation implementation **********

	@Override
	public String getNameElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__NAME;
	}

	@Override
	public String getHintsElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__HINTS;
	}

	@Override
	public QueryHintAnnotation buildHint(int index) {
		return SourceQueryHintAnnotation.buildNamedStoredProcedureQuery2_1QueryHint(this, this.annotatedElement, this.daa, index);
	}


	// ********** NamedStoredProcedureQuery2_1Annotation implementation **********
	
	String getProcedureNameElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME;
	}
	
	String getParametersElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__PARAMETERS;
	}
	
	public StoredProcedureParameterAnnotation2_1 buildParameter(int index) {
		return SourceStoredProcedureParameterAnnotation2_1.buildNamedStoredProcedureQuery2_1Parameter(this, this.annotatedElement, this.daa, index);
	}

	String getResultClassesElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES;
	}

	String getResultSetMappingsElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS;
	}

	// ***** procedure name
	public String getProcedureName() {
		return this.procedureName;
	}

	public void setProcedureName(String procedureName) {
		if (this.attributeValueHasChanged(this.procedureName, procedureName)) {
			this.procedureName = procedureName;
			this.procedureNameAdapter.setValue(procedureName);
		}
	}

	private void syncProcedureName(String procedureName) {
		String old = this.procedureName;
		this.procedureName = procedureName;
		this.firePropertyChanged(PROCEDURE_NAME_PROPERTY, old, procedureName);
	}

	private String buildProcedureName(Annotation astAnnotation) {
		return this.procedureNameAdapter.getValue(astAnnotation);
	}

	public TextRange getProcedureNameTextRange() {
		return this.procedureNameTextRange;
	}

	private TextRange buildProcedureNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.procedureNameDeclarationAdapter, astAnnotation);
	}

	private DeclarationAnnotationElementAdapter<String> buildProcedureNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, this.getProcedureNameElementName());
	}

	private AnnotationElementAdapter<String> buildProcedureNameAdapter() {
		return this.buildStringElementAdapter(this.procedureNameDeclarationAdapter);
	}


	// ***** parameters
	public ListIterable<StoredProcedureParameterAnnotation2_1> getParameters() {
		return this.storedProcedureParametersContainer.getNestedAnnotations();
	}

	public int getParametersSize() {
		return this.storedProcedureParametersContainer.getNestedAnnotationsSize();
	}

	public StoredProcedureParameterAnnotation2_1 parameterAt(int index) {
		return this.storedProcedureParametersContainer.getNestedAnnotation(index);
	}

	public StoredProcedureParameterAnnotation2_1 addParameter(int index) {
		return this.storedProcedureParametersContainer.addNestedAnnotation(index);
	}

	public void moveParameter(int targetIndex, int sourceIndex) {
		this.storedProcedureParametersContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeParameter(int index) {
		this.storedProcedureParametersContainer.removeNestedAnnotation(index);
	}


	// ***** result classes
	public ListIterable<String> getResultClasses() {
		return IterableTools.cloneLive(this.resultClasses);
	}

	public int getResultClassesSize() {
		return this.resultClasses.size();
	}

	public String resultClassAt(int index) {
		return this.resultClasses.elementAt(index);
	}

	public void addResultClass(String resultClass) {
		this.addResultClass(this.resultClasses.size(), resultClass);
	}

	public void addResultClass(int index, String resultClass) {
		this.resultClasses.add(index, resultClass);
		this.writeResultClasses();
	}

	public void moveResultClass(int targetIndex, int sourceIndex) {
		ListTools.move(this.resultClasses, targetIndex, sourceIndex);
		this.writeResultClasses();
	}

	public void removeResultClass(String resultClass) {
		this.resultClasses.remove(resultClass);
		this.writeResultClasses();
	}

	public void removeResultClass(int index) {
		this.resultClasses.remove(index);
		this.writeResultClasses();
	}

	private void writeResultClasses() {
		this.resultClassesAdapter.setValue(this.resultClasses.toArray(new String[this.resultClasses.size()]));
	}

	private void initializeResultClasses(Annotation astAnnotation) {
		String[] astResultClasses = this.resultClassesAdapter.getValue(astAnnotation);
		for (int i = 0; i < astResultClasses.length; i++) {
			this.resultClasses.add(astResultClasses[i]);
		}
	}

	private void syncResultClasses(Annotation astAnnotation) {
		String[] javaResultClasses = this.resultClassesAdapter.getValue(astAnnotation);
		this.synchronizeList(Arrays.asList(javaResultClasses), this.resultClasses, RESULT_CLASSES_LIST);
	}

	private TextRange buildResultClassesTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.resultClassesDeclarationAdapter, astAnnotation);
	}

	public boolean resultClassesTouches(int pos) {
		return this.textRangeTouches(this.resultClassesTextRange, pos);
	}

	private DeclarationAnnotationElementAdapter<String[]> buildResultClassesDeclarationAdapter() {
		return buildResultClassesArrayAnnotationElementAdapter(this.daa, JPA2_1.NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES);
	}

	private AnnotationElementAdapter<String[]> buildResultClassesAdapter() {
		return this.buildAnnotationElementAdapter(this.resultClassesDeclarationAdapter);
	}


	// ***** result set mappings
	public ListIterable<String> getResultSetMappings() {
		return IterableTools.cloneLive(this.resultSetMappings);
	}

	public int getResultSetMappingsSize() {
		return this.resultSetMappings.size();
	}

	public String resultSetMappingAt(int index) {
		return this.resultSetMappings.elementAt(index);
	}

	public void addResultSetMapping(String resultClass) {
		this.addResultSetMapping(this.resultSetMappings.size(), resultClass);
	}

	public void addResultSetMapping(int index, String resultClass) {
		this.resultSetMappings.add(index, resultClass);
		this.writeResultSetMappings();
	}

	public void moveResultSetMapping(int targetIndex, int sourceIndex) {
		ListTools.move(this.resultSetMappings, targetIndex, sourceIndex);
		this.writeResultSetMappings();
	}

	public void removeResultSetMapping(String resultSetMapping) {
		this.resultSetMappings.remove(resultSetMapping);
		this.writeResultSetMappings();
	}

	public void removeResultSetMapping(int index) {
		this.resultSetMappings.remove(index);
		this.writeResultSetMappings();
	}

	private void writeResultSetMappings() {
		this.resultSetMappingsAdapter.setValue(this.resultSetMappings.toArray(new String[this.resultSetMappings.size()]));
	}

	private void initializeResultSetMappings(Annotation astAnnotation) {
		String[] astResultClasses = this.resultSetMappingsAdapter.getValue(astAnnotation);
		for (int i = 0; i < astResultClasses.length; i++) {
			this.resultSetMappings.add(astResultClasses[i]);
		}
	}

	private void syncResultSetMappings(Annotation astAnnotation) {
		String[] javaResultClasses = this.resultSetMappingsAdapter.getValue(astAnnotation);
		this.synchronizeList(Arrays.asList(javaResultClasses), this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
	}

	private TextRange buildResultSetMappingsTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.resultSetMappingsDeclarationAdapter, astAnnotation);
	}

	public boolean resultSetMappingsTouches(int pos) {
		return this.textRangeTouches(this.resultSetMappingsTextRange, pos);
	}

	private DeclarationAnnotationElementAdapter<String[]> buildResultSetMappingsDeclarationAdapter() {
		return buildResultSetMappingsArrayAnnotationElementAdapter(this.daa, JPA2_1.NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS);
	}

	private AnnotationElementAdapter<String[]> buildResultSetMappingsAdapter() {
		return this.buildAnnotationElementAdapter(this.resultSetMappingsDeclarationAdapter);
	}
	
	// **********
	private AnnotationElementAdapter<String[]> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String[]>(this.annotatedElement, daea);
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildResultClassesArrayAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter,
			String elementName)
	{
		return buildArrayAnnotationElementAdapter(
				annotationAdapter,
				elementName,
				AnnotationStringArrayExpressionConverter.forTypes());
	}
	
	private static DeclarationAnnotationElementAdapter<String[]> buildResultSetMappingsArrayAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter,
			String elementName)
	{
		return buildArrayAnnotationElementAdapter(
				annotationAdapter,
				elementName,
				AnnotationStringArrayExpressionConverter.forStrings());
	}

	private static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter,
			String elementName,
			ExpressionConverter<String[]> converter)
	{
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(
				annotationAdapter,
				elementName,
				converter);
	}


	// ********** stored procedure parameter container **********
	/**
	 * adapt the AnnotationContainer interface to the xml schema's xmlns
	 */
	class StoredProcedureParameterAnnotationContainer
		extends AnnotationContainer<StoredProcedureParameterAnnotation2_1>
	{
		@Override
		protected String getNestedAnnotationsListName() {
			return PARAMETERS_LIST;
		}
		@Override
		protected String getElementName() {
			return SourceNamedStoredProcedureQuery2_1Annotation.this.getParametersElementName();
		}
		@Override
		protected String getNestedAnnotationName() {
			return StoredProcedureParameterAnnotation2_1.ANNOTATION_NAME;
		}
		@Override
		protected StoredProcedureParameterAnnotation2_1 buildNestedAnnotation(int index) {
			return SourceNamedStoredProcedureQuery2_1Annotation.this.buildParameter(index);
		}
	}

	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.procedureName == null) &&
				(this.storedProcedureParametersContainer.isEmpty()) &&
				(this.resultClasses.isEmpty()) &&
				(this.resultSetMappings.isEmpty());
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildNamedStoredProcedureQuery2_1AnnotationAdapter(
			AnnotatedElement annotatedElement,
			IndexedDeclarationAnnotationAdapter idaa)
	{
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedStoredProcedureQuery2_1DeclarationAnnotationAdapter(int index) 
	{
		return new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
	}

}
