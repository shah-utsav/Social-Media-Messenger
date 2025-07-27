import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Group {
  id: number;
  name: string;
  description: string;
  memberCount: number;
  isActive: boolean;
  lastActivity: Date;
  avatar: string;
}

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="groups-container">
      <div class="groups-header">
        <h1 class="gradient-text">Groups</h1>
        <p>Manage your group conversations and communities</p>
        <button class="create-group-btn" (click)="showCreateModal = true">
          <span>+</span>
          Create New Group
        </button>
      </div>
      
      <div class="groups-content">
        <div class="search-section">
          <div class="search-container">
            <input 
              type="text" 
              placeholder="Search groups..." 
              [(ngModel)]="searchQuery"
              class="search-input"
            >
            <span class="search-icon">🔍</span>
          </div>
        </div>
        
        <div class="groups-grid">
          <div 
            *ngFor="let group of filteredGroups" 
            class="group-card"
            [class.active]="group.isActive"
          >
            <div class="group-avatar">
              <span class="avatar-text">{{ group.name.charAt(0) }}</span>
            </div>
            
            <div class="group-info">
              <h3 class="group-name">{{ group.name }}</h3>
              <p class="group-description">{{ group.description }}</p>
              
              <div class="group-meta">
                <span class="member-count">{{ group.memberCount }} members</span>
                <span class="last-activity">{{ formatLastActivity(group.lastActivity) }}</span>
              </div>
            </div>
            
            <div class="group-actions">
              <button class="action-btn" title="Join Group">
                <span>👥</span>
              </button>
              <button class="action-btn" title="More options">
                <span>⋯</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Create Group Modal -->
      <div class="modal-overlay" *ngIf="showCreateModal" (click)="showCreateModal = false">
        <div class="modal-content" (click)="$event.stopPropagation()">
          <div class="modal-header">
            <h3>Create New Group</h3>
            <button class="close-btn" (click)="showCreateModal = false">×</button>
          </div>
          
          <form class="modal-form" (ngSubmit)="createGroup()">
            <div class="form-group">
              <label for="groupName">Group Name</label>
              <input 
                type="text" 
                id="groupName"
                [(ngModel)]="newGroup.name"
                name="groupName"
                class="form-input"
                placeholder="Enter group name"
                required
              >
            </div>
            
            <div class="form-group">
              <label for="groupDescription">Description</label>
              <textarea 
                id="groupDescription"
                [(ngModel)]="newGroup.description"
                name="groupDescription"
                class="form-textarea"
                placeholder="Describe your group"
                rows="3"
              ></textarea>
            </div>
            
            <div class="modal-actions">
              <button type="button" class="cancel-btn" (click)="showCreateModal = false">
                Cancel
              </button>
              <button type="submit" class="create-btn">
                Create Group
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent {
  searchQuery: string = '';
  showCreateModal: boolean = false;
  
  newGroup = {
    name: '',
    description: ''
  };

  groups: Group[] = [
    {
      id: 1,
      name: 'Project Team',
      description: 'Collaboration space for our development team',
      memberCount: 8,
      isActive: true,
      lastActivity: new Date(Date.now() - 300000),
      avatar: '👥'
    },
    {
      id: 2,
      name: 'Design Community',
      description: 'Share design inspiration and feedback',
      memberCount: 24,
      isActive: false,
      lastActivity: new Date(Date.now() - 86400000),
      avatar: '🎨'
    },
    {
      id: 3,
      name: 'Tech News',
      description: 'Latest updates in technology and innovation',
      memberCount: 156,
      isActive: false,
      lastActivity: new Date(Date.now() - 3600000),
      avatar: '📱'
    },
    {
      id: 4,
      name: 'Coffee Chat',
      description: 'Casual conversations and networking',
      memberCount: 42,
      isActive: false,
      lastActivity: new Date(Date.now() - 7200000),
      avatar: '☕'
    }
  ];

  get filteredGroups(): Group[] {
    if (!this.searchQuery.trim()) return this.groups;
    
    return this.groups.filter(group => 
      group.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
      group.description.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  createGroup() {
    if (!this.newGroup.name.trim()) return;
    
    const group: Group = {
      id: Date.now(),
      name: this.newGroup.name,
      description: this.newGroup.description,
      memberCount: 1,
      isActive: false,
      lastActivity: new Date(),
      avatar: '👥'
    };
    
    this.groups.unshift(group);
    this.newGroup = { name: '', description: '' };
    this.showCreateModal = false;
  }

  formatLastActivity(date: Date): string {
    const now = new Date();
    const diff = now.getTime() - date.getTime();
    const minutes = Math.floor(diff / 60000);
    const hours = Math.floor(diff / 3600000);
    const days = Math.floor(diff / 86400000);
    
    if (minutes < 60) {
      return `${minutes}m ago`;
    } else if (hours < 24) {
      return `${hours}h ago`;
    } else {
      return `${days}d ago`;
    }
  }
} 