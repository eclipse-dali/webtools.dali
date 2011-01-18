/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.IdClass
 */
public final class SourceIdClassAnnotation
	extends SourceAnnotation<Type>
	implements IdClassAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;

	/**
	 * We cache this here because we use the AST bindings to calculate it.<ul>
	 * <li>We do not return a calculated value because it would force a JDT
	 * parse with <em>every</em> context model <em>update</em>. (This property
	 * is one that is read during the context model <em>update</em>, as opposed
	 * to the context model <em>sync</em>.)
	 * <li>We do not calculate it during {@link #synchronizeWith(CompilationUnit)}
	 * because<ul>
	 * <li>when the class name ({@link #value}) is changed via API from the UI,
	 * we are ignoring Java change events; so we would need to calculate it
	 * during {@link #setValue(String)} which might slow down our UI a bit (with
	 * the additional parse), and setting the flag is effectively equivalent
	 * <li>when the class name ({@link #value}) is changed via API from a test,
	 * we are handling Java change events synchronously;
	 * so we would detect a change in the fully-qualified class name during
	 * {@link #synchronizeWith(CompilationUnit)}, triggering an
	 * unwanted context model <em>sync</em> (Any resource model changes via
	 * API should <em>not</em> trigger a context model <em>sync</em>.)
	 * </ul>
	 * </ul>
	 * Also, there is no change notification tied to this property since it
	 * would be fired at the same times as the change events for {@link #value}.
	 */
	// TODO any of a number of things can invalidate this: classpath change,
	// added or removed matching class
	private String fullyQualifiedClassName;
	// we need a flag since the f-q name can be null
	private boolean fqClassNameStale = true;


	public SourceIdClassAnnotation(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new AnnotatedElementAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.value = this.buildValue(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncValue(this.buildValue(astRoot));
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.value == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** IdClassAnnotation implementation **********

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.fqClassNameStale = true;
			this.valueAdapter.setValue(value);
		}
	}

	private void syncValue(String astValue) {
		if (this.attributeValueHasChanged(this.value, astValue)) {
			this.syncValue_(astValue);
		}
	}

	private void syncValue_(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.fqClassNameStale = true;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_ADAPTER, astRoot);
	}

	// ***** fully-qualified class name
	public String getFullyQualifiedClassName() {
		if (this.fqClassNameStale) {
			this.fullyQualifiedClassName = this.buildFullyQualifiedClassName();
			this.fqClassNameStale = false;
		}
		return this.fullyQualifiedClassName;
	}

	private String buildFullyQualifiedClassName() {
		return (this.value == null) ? null : this.buildFullyQualifiedClassName_();
	}

	private String buildFullyQualifiedClassName_() {
		return ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(this.buildASTRoot()));
	}


	// ********** static methods **********

	protected static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, JPA.ID_CLASS__VALUE, SimpleTypeStringExpressionConverter.instance());
	}

}
