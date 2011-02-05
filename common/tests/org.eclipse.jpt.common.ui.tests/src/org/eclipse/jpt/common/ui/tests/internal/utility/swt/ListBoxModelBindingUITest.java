/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.utility.swt;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelWrapper;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.WritableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Play around with a set of list boxes.
 */
@SuppressWarnings("nls")
public class ListBoxModelBindingUITest
	extends ApplicationWindow
{
	final TaskList taskList;
	private final WritablePropertyValueModel<TaskList> taskListHolder;
	private Text taskTextField;

	public static void main(String[] args) throws Exception {
		Window window = new ListBoxModelBindingUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private ListBoxModelBindingUITest(@SuppressWarnings("unused") String[] args) {
		super(null);
		this.taskList = new TaskList();
		this.taskListHolder = new SimplePropertyValueModel<TaskList>(this.taskList);
		this.taskList.addTask("swim");
		this.taskList.addTask("bike");
		this.taskList.addTask("run");
		Task rest = this.taskList.addTask("rest");
		this.taskList.addPriorityTask(rest);
		Task repeat = this.taskList.addTask("repeat");
		this.taskList.addPriorityTask(repeat);
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(800, 400);
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
		this.buildPrimitiveTaskListPanel(panel);
		this.buildObjectTaskListPanel(panel);

		return panel;
	}

	private void buildPrimitiveTaskListPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(50);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildUnsortedPrimitiveListPanel(panel);
		this.buildStandardSortedPrimitiveListPanel(panel);
		this.buildCustomSortedPrimitiveListPanel(panel);
	}

	private void buildObjectTaskListPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(50);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildUnsortedObjectListPanel(panel);
		this.buildStandardSortedObjectListPanel(panel);
		this.buildCustomSortedObjectListPanel(panel);
	}

	private void buildUnsortedPrimitiveListPanel(Composite parent) {
		this.buildListPanel(parent, "primitive unsorted", this.buildUnsortedPrimitiveListModel(), new SimpleCollectionValueModel<String>());
	}

	private void buildStandardSortedPrimitiveListPanel(Composite parent) {
		this.buildListPanel(parent, "primitive sorted", this.buildStandardSortedPrimitiveListModel(), new SimpleCollectionValueModel<String>());
	}

	private void buildCustomSortedPrimitiveListPanel(Composite parent) {
		this.buildListPanel(parent, "primitive reverse sorted", this.buildCustomSortedPrimitiveListModel(), new SimpleCollectionValueModel<String>());
	}

	private void buildUnsortedObjectListPanel(Composite parent) {
		this.buildListPanel(parent, "object unsorted", this.buildUnsortedObjectListModel(), this.buildPriorityTaskListAdapter());
	}

	private void buildStandardSortedObjectListPanel(Composite parent) {
		this.buildListPanel(parent, "object sorted", this.buildStandardSortedObjectListModel(), this.buildPriorityTaskListAdapter());
	}

	private void buildCustomSortedObjectListPanel(Composite parent) {
		this.buildListPanel(parent, "object reverse sorted", this.buildCustomSortedObjectListModel(), this.buildPriorityTaskListAdapter());
	}

	private ListValueModel<String> buildUnsortedPrimitiveListModel() {
		return this.buildPrimitiveTaskListAdapter();
	}

	private ListValueModel<String> buildStandardSortedPrimitiveListModel() {
		return new SortedListValueModelWrapper<String>(this.buildPrimitiveTaskListAdapter());
	}

	private ListValueModel<String> buildCustomSortedPrimitiveListModel() {
		return new SortedListValueModelWrapper<String>(this.buildPrimitiveTaskListAdapter(), this.buildCustomStringComparator());
	}

	private ListValueModel<Task> buildUnsortedObjectListModel() {
		return this.buildObjectTaskListAdapter();
	}

	private ListValueModel<Task> buildStandardSortedObjectListModel() {
		return new SortedListValueModelWrapper<Task>(this.buildObjectTaskListAdapter());
	}

	private ListValueModel<Task> buildCustomSortedObjectListModel() {
		return new SortedListValueModelWrapper<Task>(this.buildObjectTaskListAdapter(), this.buildCustomTaskComparator());
	}

	private <E> org.eclipse.swt.widgets.List buildListPanel(Composite parent, String label, ListValueModel<E> model, WritableCollectionValueModel<E> selectedItemsModel) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayout(new FormLayout());

		Label listLabel = new Label(panel, SWT.LEFT | SWT.VERTICAL);
		listLabel.setText(label);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0, 3);
			fd.bottom = new FormAttachment(0, 20);
			fd.left = new FormAttachment(0, 5);
			fd.right = new FormAttachment(100);
		listLabel.setLayoutData(fd);

		org.eclipse.swt.widgets.List listBox = new org.eclipse.swt.widgets.List(panel, SWT.MULTI | SWT.BORDER);
		fd = new FormData();
			fd.top = new FormAttachment(listLabel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		listBox.setLayoutData(fd);
		SWTTools.bind(model, selectedItemsModel, listBox);  // use #toString()
		return listBox;
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

	private WritableCollectionValueModel<Task> buildPriorityTaskListAdapter() {
		return new PriorityTaskListAdapter(this.taskListHolder);
	}

	static class PriorityTaskListAdapter
		extends CollectionAspectAdapter<TaskList, Task>
		implements WritableCollectionValueModel<Task>
	{
		PriorityTaskListAdapter(WritablePropertyValueModel<TaskList> taskListHolder) {
			super(taskListHolder, TaskList.PRIORITY_TASKS_COLLECTION);
		}
		@Override
		protected Iterator<Task> iterator_() {
			return this.subject.priorityTasks();
		}
		public void setValues(Iterable<Task> values) {
			this.subject.setPriorityTasks(values);
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
		this.buildAddPriorityTaskACI().fill(panel);
		this.buildRemovePriorityTaskACI().fill(panel);
		this.buildClearPriorityTasksACI().fill(panel);
		return panel;
	}

	private ActionContributionItem buildClearListACI() {
		Action action = new Action("clear list", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ListBoxModelBindingUITest.this.clearTasks();
			}
		};
		action.setToolTipText("clear all the tasks");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildClearModelACI() {
		Action action = new Action("clear model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ListBoxModelBindingUITest.this.clearModel();
			}
		};
		action.setToolTipText("clear the task list model");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildRestoreModelACI() {
		Action action = new Action("restore model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ListBoxModelBindingUITest.this.restoreModel();
			}
		};
		action.setToolTipText("restore the task list model");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildAddPriorityTaskACI() {
		Action action = new Action("add priority", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ListBoxModelBindingUITest.this.addPriorityTask();
			}
		};
		action.setToolTipText("add a task to the priority tasks");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildRemovePriorityTaskACI() {
		Action action = new Action("remove priority", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ListBoxModelBindingUITest.this.removePriorityTask();
			}
		};
		action.setToolTipText("remove a task from the priority tasks");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildClearPriorityTasksACI() {
		Action action = new Action("clear priority", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				ListBoxModelBindingUITest.this.clearPriorityTasks();
			}
		};
		action.setToolTipText("clear the priority tasks");
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
				ListBoxModelBindingUITest.this.addTask();
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
				ListBoxModelBindingUITest.this.removeTask();
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

	void addPriorityTask() {
		Iterator<Task> tasks = this.taskList.tasks();
		while (tasks.hasNext()) {
			if (this.taskList.addPriorityTask(tasks.next())) {
				return;
			}
		}
	}

	void removePriorityTask() {
		Iterator<Task> pTasks = this.taskList.priorityTasks();
		if (pTasks.hasNext()) {
			this.taskList.removePriorityTask(pTasks.next());
		}
	}

	void clearPriorityTasks() {
		this.taskList.clearPriorityTasks();
	}


	// ********** TaskList **********

	// note absence of validation...
	public static class TaskList extends AbstractModel {
		private final List<String> taskNames = new ArrayList<String>();
			public static final String TASK_NAMES_LIST = "taskNames";
		private final List<Task> tasks = new ArrayList<Task>();
			public static final String TASKS_LIST = "tasks";
		private final Collection<Task> priorityTasks = new HashSet<Task>();
			public static final String PRIORITY_TASKS_COLLECTION = "priorityTasks";
		public TaskList() {
			super();
		}
		public ListIterator<String> taskNames() {
			return this.taskNames.listIterator();
		}
		public ListIterator<Task> tasks() {
			return this.tasks.listIterator();
		}
		public Iterator<Task> priorityTasks() {
			return this.priorityTasks.iterator();
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
				this.removeItemFromList(index, this.taskNames, TASK_NAMES_LIST);
				// assume the indexes match...
				Task removedTask = this.removeItemFromList(index, this.tasks, TASKS_LIST);
				this.removeItemFromCollection(removedTask, this.priorityTasks, PRIORITY_TASKS_COLLECTION);
			}
		}
		public void clearTasks() {
			this.clearCollection(this.priorityTasks, PRIORITY_TASKS_COLLECTION);
			this.clearList(this.taskNames, TASK_NAMES_LIST);
			this.clearList(this.tasks, TASKS_LIST);
		}
		public boolean addPriorityTask(Task task) {
			return this.addItemToCollection(task, this.priorityTasks, PRIORITY_TASKS_COLLECTION);
		}		
		public void removePriorityTask(Task task) {
			this.removeItemFromCollection(task, this.priorityTasks, PRIORITY_TASKS_COLLECTION);
		}
		public void clearPriorityTasks() {
			this.clearCollection(this.priorityTasks, PRIORITY_TASKS_COLLECTION);
		}
		public void setPriorityTasks(Iterable<Task> tasks) {
			this.priorityTasks.clear();
			CollectionTools.addAll(this.priorityTasks, tasks);
			this.fireCollectionChanged(PRIORITY_TASKS_COLLECTION, this.priorityTasks);
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
