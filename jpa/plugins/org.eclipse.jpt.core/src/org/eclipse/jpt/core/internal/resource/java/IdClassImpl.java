package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class IdClassImpl extends AbstractAnnotationResource<Type> implements IdClass
{
	private final AnnotationElementAdapter<String> valueAdapter;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ID_CLASS);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();

	private String value;

	private String fullyQualifiedValue;

	public IdClassImpl(JavaResource parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter<String>(type, VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return JPA.ID_CLASS;
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

	public void updateFromJava(CompilationUnit astRoot) {
		this.setValue(this.valueAdapter.getValue(astRoot));
		this.setFullyQualifiedClass(fullyQualifiedClass(astRoot));
	}

	//TODO copied from AbstractRelationshipMappingAnnotation
	private String fullyQualifiedClass(CompilationUnit astRoot) {
		if (getValue() == null) {
			return null;
		}
		Expression expression = this.valueAdapter.expression(astRoot);
		if (expression.getNodeType() == ASTNode.TYPE_LITERAL) {
			ITypeBinding resolvedTypeBinding = ((TypeLiteral) expression).getType().resolveBinding();
			if (resolvedTypeBinding != null) {
				return resolvedTypeBinding.getQualifiedName();
			}
		}
		return null;
	}
	
	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, JPA.ID_CLASS__VALUE, SimpleTypeStringExpressionConverter.instance());
	}

}
