/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;

/**
 * copied from org.eclipse.jdt.internal.corext.dom.GenericVisitor
 */
public class GenericVisitor extends ASTVisitor {

	public GenericVisitor() {
		super();
	}

	public GenericVisitor(boolean visitJavadocTags) {
		super(visitJavadocTags);
	}

	// ********** hooks for subclasses **********

	protected boolean visit_(@SuppressWarnings("unused") ASTNode node) {
		return true;
	}

	protected void endVisit_(@SuppressWarnings("unused") ASTNode node) {
		// do nothing
	}

	// ********** overrides **********

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ArrayAccess node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ArrayCreation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ArrayInitializer node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ArrayType node) {
		return visit_(node);
	}
	@Override
	public boolean visit(AssertStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(Assignment node) {
		return visit_(node);
	}
	@Override
	public boolean visit(Block node) {
		return visit_(node);
	}
	@Override
	public boolean visit(BooleanLiteral node) {
		return visit_(node);
	}
	@Override
	public boolean visit(BreakStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(CastExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(CatchClause node) {
		return visit_(node);
	}
	@Override
	public boolean visit(CharacterLiteral node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ClassInstanceCreation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(CompilationUnit node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ConditionalExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ConstructorInvocation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ContinueStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(DoStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(EmptyStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ExpressionStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(FieldAccess node) {
		return visit_(node);
	}
	@Override
	public boolean visit(FieldDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ForStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(IfStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ImportDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(InfixExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(InstanceofExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(Initializer node) {
		return visit_(node);
	}
	@Override
	public boolean visit(Javadoc node) {
		return (super.visit(node)) ? visit_(node) : false;
	}
	@Override
	public boolean visit(LabeledStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(MethodDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(MethodInvocation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(NullLiteral node) {
		return visit_(node);
	}
	@Override
	public boolean visit(NumberLiteral node) {
		return visit_(node);
	}
	@Override
	public boolean visit(PackageDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ParenthesizedExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(PostfixExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(PrefixExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(PrimitiveType node) {
		return visit_(node);
	}
	@Override
	public boolean visit(QualifiedName node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ReturnStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SimpleName node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SimpleType node) {
		return visit_(node);
	}
	@Override
	public boolean visit(StringLiteral node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SuperConstructorInvocation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SuperFieldAccess node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SuperMethodInvocation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SwitchCase node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SwitchStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SynchronizedStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ThisExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ThrowStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(TryStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(TypeDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(TypeLiteral node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		return visit_(node);
	}
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		return visit_(node);
	}
	@Override
	public boolean visit(WhileStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(BlockComment node) {
		return visit_(node);
	}
	@Override
	public boolean visit(EnhancedForStatement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(EnumDeclaration node) {
		return visit_(node);
	}
	@Override
	public boolean visit(LineComment node) {
		return visit_(node);
	}
	@Override
	public boolean visit(MarkerAnnotation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(MemberRef node) {
		return visit_(node);
	}
	@Override
	public boolean visit(MemberValuePair node) {
		return visit_(node);
	}
	@Override
	public boolean visit(MethodRef node) {
		return visit_(node);
	}
	@Override
	public boolean visit(MethodRefParameter node) {
		return visit_(node);
	}
	@Override
	public boolean visit(Modifier node) {
		return visit_(node);
	}
	@Override
	public boolean visit(NormalAnnotation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(ParameterizedType node) {
		return visit_(node);
	}
	@Override
	public boolean visit(QualifiedType node) {
		return visit_(node);
	}
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		return visit_(node);
	}
	@Override
	public boolean visit(TagElement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(TextElement node) {
		return visit_(node);
	}
	@Override
	public boolean visit(TypeParameter node) {
		return visit_(node);
	}
	@Override
	public boolean visit(WildcardType node) {
		return visit_(node);
	}

	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ArrayAccess node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ArrayCreation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ArrayInitializer node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ArrayType node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(AssertStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(Assignment node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(Block node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(BooleanLiteral node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(BreakStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(CastExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(CatchClause node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(CharacterLiteral node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ClassInstanceCreation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(CompilationUnit node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ConditionalExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ConstructorInvocation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ContinueStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(DoStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(EmptyStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ExpressionStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(FieldAccess node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(FieldDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ForStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(IfStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ImportDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(InfixExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(InstanceofExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(Initializer node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(Javadoc node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(LabeledStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(MethodDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(MethodInvocation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(NullLiteral node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(NumberLiteral node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(PackageDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ParenthesizedExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(PostfixExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(PrefixExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(PrimitiveType node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(QualifiedName node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ReturnStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SimpleName node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SimpleType node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(StringLiteral node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SuperConstructorInvocation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SuperFieldAccess node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SuperMethodInvocation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SwitchCase node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SwitchStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SynchronizedStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ThisExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ThrowStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(TryStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(TypeDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(TypeDeclarationStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(TypeLiteral node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SingleVariableDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(VariableDeclarationExpression node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(VariableDeclarationStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(VariableDeclarationFragment node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(WhileStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(AnnotationTypeDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(AnnotationTypeMemberDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(BlockComment node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(EnhancedForStatement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(EnumConstantDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(EnumDeclaration node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(LineComment node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(MarkerAnnotation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(MemberRef node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(MemberValuePair node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(MethodRef node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(MethodRefParameter node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(Modifier node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(NormalAnnotation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(ParameterizedType node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(QualifiedType node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(SingleMemberAnnotation node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(TagElement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(TextElement node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(TypeParameter node) {
		endVisit_(node);
	}
	@Override
	public void endVisit(WildcardType node) {
		endVisit_(node);
	}

}

