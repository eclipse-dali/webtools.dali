package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class IdClassImpl extends AbstractAnnotationResource<Type> implements IdClass
{
	private static final String ANNOTATION_NAME = JPA.ID_CLASS;

	private final AnnotationElementAdapter<String> valueAdapter;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();

	private String value;

	private String fullyQualifiedValue;

	public IdClassImpl(JavaResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
		this.valueAdapter.setValue(value);
	}

	public String getFullyQualifiedClass() {
		return this.fullyQualifiedValue;
	}
	
	private void setFullyQualifiedClass(String qualifiedClass) {
		this.fullyQualifiedValue = qualifiedClass;
		//change notification
	}

	public ITextRange valueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.VALUE_ADAPTER, astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		this.setValue(this.valueAdapter.getValue(astRoot));
		this.setFullyQualifiedClass(fullyQualifiedClass(astRoot));
	}

	private String fullyQualifiedClass(CompilationUnit astRoot) {
		if (getValue() == null) {
			return null;
		}
		return JDTTools.resolveFullyQualifiedName(this.valueAdapter.expression(astRoot));
	}
	
	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, JPA.ID_CLASS__VALUE, SimpleTypeStringExpressionConverter.instance());
	}

	
	public static class IdClassAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final IdClassAnnotationDefinition INSTANCE = new IdClassAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private IdClassAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new IdClassImpl(parent, (Type) member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
