package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class JavaSingleRelationshipMappingContext
	extends JavaRelationshipMappingContext
{
	private Collection<JoinColumnContext> joinColumnContexts;

	protected JavaSingleRelationshipMappingContext(
			IContext parentContext, JavaSingleRelationshipMapping mapping) {
		super(parentContext, mapping);
		this.joinColumnContexts = buildJoinColumnContexts();
	}
	
	protected Collection<JoinColumnContext> buildJoinColumnContexts() {
		Collection<JoinColumnContext> contexts = new ArrayList<JoinColumnContext>();
		for (Iterator i = getMapping().getJoinColumns().iterator(); i.hasNext(); ) {
			IJoinColumn column = (IJoinColumn) i.next();
			contexts.add(new JoinColumnContext(this, column));
		}
		return contexts;
	}
	
	protected ISingleRelationshipMapping getMapping() {
		return (ISingleRelationshipMapping) super.getMapping();
	}
	
	protected ITable getTable() {
		IEntity entity = getMapping().getEntity();
		if (entity == null) {
			return null;
		}
		return entity.getTable();
	}
	
	public void refreshDefaultsInternal(DefaultsContext defaultsContext) {
		super.refreshDefaultsInternal(defaultsContext);
		DefaultsContext joinColumnsDefaultsContext = wrapDefaultsContextForJoinColumn(defaultsContext);
		for (JoinColumnContext context : this.joinColumnContexts) {
			context.refreshDefaults(joinColumnsDefaultsContext);
		}
	}
	
	protected DefaultsContext wrapDefaultsContextForJoinColumn(final DefaultsContext defaultsContext) {
		return new DefaultsContext() {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_TABLE_KEY)) {
					ITable table = getTable();
					if (table == null) {
						return null;
					}
					return table.getName();
				}
				return defaultsContext.getDefault(key);
			}
			
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return defaultsContext.persistentType(fullyQualifiedTypeName);
			}
		};
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		addJoinColumnMessages(messages);
	}
	
	protected void addJoinColumnMessages(List<IMessage> messages) {
		ISingleRelationshipMapping mapping = getMapping();
		ITypeMapping typeMapping = mapping.typeMapping();
		
		for (IJoinColumn joinColumn : mapping.getJoinColumns()) {
			String table = joinColumn.getTable();
			boolean doContinue = joinColumn.isConnected();
			
			if (doContinue && typeMapping.tableNameIsInvalid(table)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {table, joinColumn.getName()}, 
						joinColumn, joinColumn.getTableTextRange())
				);
				doContinue = false;
			}
			
			if (doContinue && ! joinColumn.isResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, joinColumn.getNameTextRange())
				);
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, joinColumn.getReferencedColumnNameTextRange())
				);
			}
		}
	}
}
