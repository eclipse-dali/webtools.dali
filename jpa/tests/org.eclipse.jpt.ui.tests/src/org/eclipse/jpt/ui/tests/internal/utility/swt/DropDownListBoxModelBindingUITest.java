/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.utility.swt;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Play around with a set of read-only combo-boxes.
 */
@SuppressWarnings("nls")
public class DropDownListBoxModelBindingUITest
	extends ApplicationWindow
{
	final TaskList taskList;
	private final WritablePropertyValueModel<TaskList> taskListHolder;
	private Text taskTextField;

	public static void main(String[] args) throws Exception {
		Window window = new DropDownListBoxModelBindingUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private DropDownListBoxModelBindingUITest(@SuppressWarnings("unused") String[] args) {
		super(null);
		this.taskList = new TaskList();
		this.taskListHolder = new SimplePropertyValueModel<TaskList>(this.taskList);
		this.taskList.addTask("swim");
		this.taskList.addTask("bike");
		this.taskList.addTask("run");
		Task rest = this.taskList.addTask("rest");
		this.taskList.addTask("repeat");
		this.taskList.setPriorityTask(rest);
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(ClassTools.shortClassNameForObject(this));
		parent.setSize(800, 300);
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayout(new FormLayout());
		Control taskListPanel = this.buildTaskListPanel(mainPanel);
		this.buildControlPanel(mainPanel, taskListPanel);
		return mainPanel;
	}

	private Control buildTaskListPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100, -30);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FormLayout());
		this.buildTaskListPanel(panel, false);  // false = native (Combo)
		this.buildTaskListPanel(panel, true);  // true = custom (CCombo)

		return panel;
	}

	private Control buildTaskListPanel(Composite parent, boolean custom) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(custom ? 50 : 0);
			fd.bottom = new FormAttachment(custom ? 100 : 50);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FormLayout());
		this.buildPrimitiveTaskListPanel(panel, custom);
		this.buildObjectTaskListPanel(panel, custom);

		return panel;
	}

	private void buildPrimitiveTaskListPanel(Composite parent, boolean custom) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(50);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildUnsortedPrimitiveListPanel(panel, custom);
		this.buildStandardSortedPrimitiveListPanel(panel, custom);
		this.buildCustomSortedPrimitiveListPanel(panel, custom);
	}

	private void buildObjectTaskListPanel(Composite parent, boolean custom) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(50);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildUnsortedObjectListPanel(panel, custom);
		this.buildStandardSortedObjectListPanel(panel, custom);
		this.buildCustomSortedObjectListPanel(panel, custom);
	}

	private void buildUnsortedPrimitiveListPanel(Composite parent, boolean custom) {
		String label = "primitive unsorted";
		if (custom) label += " (custom)";
		this.buildComboBoxPanel(parent, label, this.buildUnsortedPrimitiveListModel(), this.buildPriorityTaskNameAdapter(), custom);
	}

	private void buildStandardSortedPrimitiveListPanel(Composite parent, boolean custom) {
		String label = "primitive sorted";
		if (custom) label += " (custom)";
		this.buildComboBoxPanel(parent, label, this.buildStandardSortedPrimitiveListModel(), this.buildPriorityTaskNameAdapter(), custom);
	}

	private void buildCustomSortedPrimitiveListPanel(Composite parent, boolean custom) {
		String label = "primitive reverse sorted";
		if (custom) label += " (custom)";
		this.buildComboBoxPanel(parent, label, this.buildCustomSortedPrimitiveListModel(), this.buildPriorityTaskNameAdapter(), custom);
	}

	private void buildUnsortedObjectListPanel(Composite parent, boolean custom) {
		String label = "object unsorted";
		if (custom) label += " (custom)";
		this.buildComboBoxPanel(parent, label, this.buildUnsortedObjectListModel(), this.buildPriorityTaskAdapter(), custom);
	}

	private void buildStandardSortedObjectListPanel(Composite parent, boolean custom) {
		String label = "object sorted";
		if (custom) label += " (custom)";
		this.buildComboBoxPanel(parent, label, this.buildStandardSortedObjectListModel(), this.buildPriorityTaskAdapter(), custom);
	}

	private void buildCustomSortedObjectListPanel(Composite parent, boolean custom) {
		String label = "object reverse sorted";
		if (custom) label += " (custom)";
		this.buildComboBoxPanel(parent, label, this.buildCustomSortedObjectListModel(), this.buildPriorityTaskAdapter(), custom);
	}

	private ListValueModel<String> buildUnsortedPrimitiveListModel() {
		return this.buildPrimitiveTaskListAdapter();
	}

	private ListValueModel<String> buildStandardSortedPrimitiveListModel() {
		return new SortedListValueModelAdapter<String>(this.buildPrimitiveTaskListAdapter());
	}

	private ListValueModel<String> buildCustomSortedPrimitiveListModel() {
		return new SortedListValueModelAdapter<String>(this.buildPrimitiveTaskListAdapter(), this.buildCustomStringComparator());
	}

	private ListValueModel<Task> buildUnsortedObjectListModel() {
		return this.buildObjectTaskListAdapter();
	}

	private ListValueModel<Task> buildStandardSortedObjectListModel() {
		return new SortedListValueModelAdapter<Task>(this.buildObjectTaskListAdapter());
	}

	private ListValueModel<Task> buildCustomSortedObjectListModel() {
		return new SortedListValueModelAdapter<Task>(this.buildObjectTaskListAdapter(), this.buildCustomTaskComparator());
	}

	private <E> void buildComboBoxPanel(Composite parent, String label, ListValueModel<E> model, WritablePropertyValueModel<E> selectedItemModel, boolean custom) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayout(new FormLayout());

		Label comboBoxLabel = new Label(panel, SWT.LEFT | SWT.VERTICAL);
		comboBoxLabel.setText(label);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0, 3);
			fd.bottom = new FormAttachment(0, 20);
			fd.left = new FormAttachment(0, 5);
			fd.right = new FormAttachment(100);
		comboBoxLabel.setLayoutData(fd);

		Control comboBox = this.buildComboBox(panel, custom);
		fd = new FormData();
			fd.top = new FormAttachment(comboBoxLabel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		comboBox.setLayoutData(fd);
		if (custom) {
			SWTTools.bind(model, selectedItemModel, (CCombo) comboBox);  // use #toString()
		} else {
			SWTTools.bind(model, selectedItemModel, (Combo) comboBox);  // use #toString()
		}
	}

	private Control buildComboBox(Composite parent, boolean custom) {
		int style = SWT.READ_ONLY;
		return custom ? new CCombo(parent, style) : new Combo(parent, style);
	}

	private Comparator<String> buildCustomStringComparator() {
		return new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s2.compareTo(s1);
			}
		};
	}

	private Comparator<Task> buildCustomTaskComparator() {
		return new Comparator<Task>() {
			public int compare(Task to1, Task to2) {
				return to2.compareTo(to1);
			}
		};
	}

	private ListValueModel<String> buildPrimitiveTaskListAdapter() {
		return new ListAspectAdapter<TaskList, String>(this.taskListHolder, TaskList.TASK_NAMES_LIST) {
			@Override
			protected ListIterator<String> listIterator_() {
				return this.subject.taskNames();
			}
		};
	}

	private ListValueModel<Task> buildObjectTaskListAdapter() {
		return new ListAspectAdapter<TaskList, Task>(this.taskListHolder, TaskList.TASKS_LIST) {
			@Override
			protected ListIterator<Task> listIterator_() {
				return this.subject.tasks();
			}
		};
	}

	private WritablePropertyValueModel<Task> buildPriorityTaskAdapter() {
		return new PriorityTaskAdapter(this.taskListHolder);
	}

	static class PriorityTaskAdapter
		extends PropertyAspectAdapter<TaskList, Task>
	{
		PriorityTaskAdapter(WritablePropertyValueModel<TaskList> taskListHolder) {
			super(taskListHolder, TaskList.PRIORITY_TASK_PROPERTY);
		}
		@Override
		protected Task buildValue_() {
			return this.subject.getPriorityTask();
		}
		@Override
		protected void setValue_(Task value) {
			this.subject.setPriorityTask(value);
		}
	}

	private WritablePropertyValueModel<String> buildPriorityTaskNameAdapter() {
		return new PriorityTaskNameAdapter(this.taskListHolder);
	}

	static class PriorityTaskNameAdapter
		extends PropertyAspectAdapter<TaskList, String>
	{
		PriorityTaskNameAdapter(WritablePropertyValueModel<TaskList> taskListHolder) {
			super(taskListHolder, TaskList.PRIORITY_TASK_NAME_PROPERTY);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getPriorityTaskName();
		}
		@Override
		protected void setValue_(String value) {
			// ignore
		}
	}

	private void buildControlPanel(Composite parent, Control taskListPanel) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(taskListPanel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FormLayout());
		Control misc = this.buildMiscTaskPanel(panel);
		this.buildAddRemoveTaskPanel(panel, misc);
	}

	// is there a better way to associate an ACI with form data?
	private Control buildMiscTaskPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(100, -400);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildClearListACI().fill(panel);
		this.buildClearModelACI().fill(panel);
		this.buildRestoreModelACI().fill(panel);
		this.buildChangePriorityTaskACI().fill(panel);
		this.buildClearPriorityTaskACI().fill(panel);
		return panel;
	}

	private ActionContributionItem buildClearListACI() {
		Action action = new Action("clear list", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DropDownListBoxModelBindingUITest.this.clearTasks();
			}
		};
		action.setToolTipText("clear all the tasks");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildClearModelACI() {
		Action action = new Action("clear model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DropDownListBoxModelBindingUITest.this.clearModel();
			}
		};
		action.setToolTipText("clear the task list model");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildRestoreModelACI() {
		Action action = new Action("restore model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DropDownListBoxModelBindingUITest.this.restoreModel();
			}
		};
		action.setToolTipText("restore the task list model");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildChangePriorityTaskACI() {
		Action action = new Action("change priority", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DropDownListBoxModelBindingUITest.this.changePriorityTask();
			}
		};
		action.setToolTipText("change the priority task");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildClearPriorityTaskACI() {
		Action action = new Action("clear priority", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DropDownListBoxModelBindingUITest.this.clearPriorityTask();
			}
		};
		action.setToolTipText("clear the priority task");
		return new ActionContributionItem(action);
	}

	private void buildAddRemoveTaskPanel(Composite parent, Control clearButton) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(clearButton);
		panel.setLayoutData(fd);

		panel.setLayout(new FormLayout());
		Control addButton = this.buildAddButton(panel);
		Control removeButton = this.buildRemoveButton(panel);
		this.buildTaskTextField(panel, addButton, removeButton);
	}

	// is there a better way to associate an ACI with form data?
	private Control buildAddButton(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(0, 50);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildAddACI().fill(panel);
		return panel;
	}

	private ActionContributionItem buildAddACI() {
		Action action = new Action("add", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DropDownListBoxModelBindingUITest.this.addTask();
			}
		};
		action.setToolTipText("add a task with the name in the entry field");
		return new ActionContributionItem(action);
	}

	// is there a better way to associate an ACI with form data?
	private Control buildRemoveButton(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(100, -50);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildRemoveACI().fill(panel);
		return panel;
	}

	private ActionContributionItem buildRemoveACI() {
		Action action = new Action("remove", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DropDownListBoxModelBindingUITest.this.removeTask();
			}
		};
		action.setToolTipText("remove the task with the name in the entry field");
		return new ActionContributionItem(action);
	}

	private void buildTaskTextField(Composite parent, Control addButton, Control removeButton) {
		this.taskTextField = new Text(parent, SWT.SINGLE | SWT.BORDER);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(addButton);
			fd.right = new FormAttachment(removeButton);
		this.taskTextField.setLayoutData(fd);
	}

	private String taskTextFieldText() {
		return this.taskTextField.getText();
	}

	void addTask() {
		String taskText = this.taskTextFieldText();
		if (taskText.length() != 0) {
			this.taskList.addTask(taskText);
		}
	}

	void removeTask() {
		String task = this.taskTextFieldText();
		if (task.length() != 0) {
			this.taskList.removeTask(task);
		}
	}

	void clearTasks() {
		this.taskList.clearTasks();
	}

	void clearModel() {
		this.taskListHolder.setValue(null);
	}

	void restoreModel() {
		this.taskListHolder.setValue(this.taskList);
	}

	void changePriorityTask() {
		boolean found = false;
		for (Task task : this.taskList.getTasks()) {
			if (this.taskList.getPriorityTask() == task) {
				found = true;
			} else {
				if (found) {
					this.taskList.setPriorityTask(task);
					return;
				}
			}
		}
		Iterator<Task> tasks = this.taskList.tasks();
		if (tasks.hasNext()) {
			this.taskList.setPriorityTask(tasks.next());
		}
	}

	void clearPriorityTask() {
		this.taskList.setPriorityTask(null);
	}


	// ********** TaskList **********

	// note absence of validation...
	public static class TaskList extends AbstractModel {
		private final List<String> taskNames = new ArrayList<String>();
			public static final String TASK_NAMES_LIST = "taskNames";
		private final List<Task> tasks = new ArrayList<Task>();
			public static final String TASKS_LIST = "tasks";
		private String priorityTaskName = null;
			public static final String PRIORITY_TASK_NAME_PROPERTY = "priorityTaskName";
		private Task priorityTask = null;
			public static final String PRIORITY_TASK_PROPERTY = "priorityTask";
		public TaskList() {
			super();
		}
		public ListIterator<String> taskNames() {
			return this.taskNames.listIterator();
		}
		public Iterable<Task> getTasks() {
			return this.tasks;
		}
		public ListIterator<Task> tasks() {
			return this.tasks.listIterator();
		}
		public String getPriorityTaskName() {
			return this.priorityTaskName;
		}
		public Task getPriorityTask() {
			return this.priorityTask;
		}
		public Task addTask(String taskName) {
			this.addItemToList(taskName, this.taskNames, TASK_NAMES_LIST);
			Task task = new Task(taskName);
			this.addItemToList(task, this.tasks, TASKS_LIST);
			return task;
		}		
		public void removeTask(String taskName) {
			int index = this.taskNames.indexOf(taskName);
			if (index != -1) {
				Task task = this.tasks.get(index);
				if (task == this.priorityTask) {
					this.setPriorityTask(null);
				}
				// assume the indexes match...
				this.removeItemFromList(index, this.taskNames, TASK_NAMES_LIST);
				this.removeItemFromList(index, this.tasks, TASKS_LIST);
			}
		}
		public void clearTasks() {
			this.setPriorityTask(null);
			this.clearList(this.taskNames, TASK_NAMES_LIST);
			this.clearList(this.tasks, TASKS_LIST);
		}
		private void setPriorityTaskName(String priorityTaskName) {
			String old = this.priorityTaskName;
			this.priorityTaskName = priorityTaskName;
			this.firePropertyChanged(PRIORITY_TASK_NAME_PROPERTY, old, priorityTaskName);
		}
		public void setPriorityTask(Task priorityTask) {
			Task old = this.priorityTask;
			this.priorityTask = priorityTask;
			this.firePropertyChanged(PRIORITY_TASK_PROPERTY, old, priorityTask);
			this.setPriorityTaskName((priorityTask == null) ? null : priorityTask.getName());
		}
	}


	// ********** Task **********

	public static class Task extends AbstractModel implements Displayable {
		private String name;
		private int instanceCount;
		private static int INSTANCE_COUNT = 1;
		public Task(String name) {
			this.name = name;
			this.instanceCount = INSTANCE_COUNT++;
		}
		public String displayString() {
			return this.name + ": " + this.instanceCount;
		}
		public int compareTo(Displayable o) {
			return DEFAULT_COMPARATOR.compare(this, o);
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(DISPLAY_STRING_PROPERTY, old, name);
		}
		@Override
		public String toString() {
			return this.displayString();
		}
	}

	public interface Displayable extends Model, Comparable<Displayable> {
	
		String displayString();
			String DISPLAY_STRING_PROPERTY = "displayString";
	
	
		// ********** helper implementations **********
	
		Collator DEFAULT_COLLATOR = Collator.getInstance();
	
		Comparator<Displayable> DEFAULT_COMPARATOR =
			new Comparator<Displayable>() {
				public int compare(Displayable d1, Displayable d2) {
					// disallow duplicates based on object identity
					if (d1 == d2) {
						return 0;
					}
	
					// first compare display strings using the default collator
					int result = DEFAULT_COLLATOR.compare(d1.displayString(), d2.displayString());
					if (result != 0) {
						return result;
					}
	
					// then compare using object-id
					result = System.identityHashCode(d1) - System.identityHashCode(d2);
					if (result != 0) {
						return result;
					}
	
					// It's unlikely that we get to this point; but, just in case, we will return -1.
					// Unfortunately, this introduces some mild unpredictability to the sort order
					// (unless the objects are always passed into this method in the same order).
					return -1;		// if all else fails, indicate that o1 < o2
				}
				@Override
				public String toString() {
					return "Displayable.DEFAULT_COMPARATOR";
				}
			};
	
	}
}
