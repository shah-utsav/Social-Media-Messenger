import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="profile-container">
      <div class="profile-header">
        <h1 class="gradient-text">Profile</h1>
        <p>Manage your account settings and preferences</p>
      </div>
      
      <div class="profile-content">
        <div class="profile-card">
          <div class="profile-avatar-section">
            <div class="profile-avatar">
              <span class="avatar-text">👤</span>
            </div>
            <button class="change-avatar-btn">Change Avatar</button>
          </div>
          
          <div class="profile-form">
            <div class="form-group">
              <label for="name">Full Name</label>
              <input 
                type="text" 
                id="name"
                [(ngModel)]="profile.name"
                class="form-input"
                placeholder="Enter your full name"
              >
            </div>
            
            <div class="form-group">
              <label for="email">Email</label>
              <input 
                type="email" 
                id="email"
                [(ngModel)]="profile.email"
                class="form-input"
                placeholder="Enter your email"
              >
            </div>
            
            <div class="form-group">
              <label for="bio">Bio</label>
              <textarea 
                id="bio"
                [(ngModel)]="profile.bio"
                class="form-textarea"
                placeholder="Tell us about yourself"
                rows="3"
              ></textarea>
            </div>
            
            <div class="form-group">
              <label for="status">Status</label>
              <select 
                id="status"
                [(ngModel)]="profile.status"
                class="form-select"
              >
                <option value="online">Online</option>
                <option value="away">Away</option>
                <option value="busy">Busy</option>
                <option value="offline">Offline</option>
              </select>
            </div>
            
            <button class="save-btn" (click)="saveProfile()">
              Save Changes
            </button>
          </div>
        </div>
        
        <div class="settings-card">
          <h3>Settings</h3>
          
          <div class="setting-item">
            <div class="setting-info">
              <h4>Notifications</h4>
              <p>Receive notifications for new messages</p>
            </div>
            <label class="toggle-switch">
              <input type="checkbox" [(ngModel)]="settings.notifications">
              <span class="toggle-slider"></span>
            </label>
          </div>
          
          <div class="setting-item">
            <div class="setting-info">
              <h4>Dark Mode</h4>
              <p>Use dark theme for better experience</p>
            </div>
            <label class="toggle-switch">
              <input type="checkbox" [(ngModel)]="settings.darkMode">
              <span class="toggle-slider"></span>
            </label>
          </div>
          
          <div class="setting-item">
            <div class="setting-info">
              <h4>Auto-reply</h4>
              <p>Send automatic replies when away</p>
            </div>
            <label class="toggle-switch">
              <input type="checkbox" [(ngModel)]="settings.autoReply">
              <span class="toggle-slider"></span>
            </label>
          </div>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  profile = {
    name: 'John Doe',
    email: 'john.doe@example.com',
    bio: 'Software developer passionate about creating amazing user experiences.',
    status: 'online'
  };

  settings = {
    notifications: true,
    darkMode: true,
    autoReply: false
  };

  saveProfile() {
    // Simulate saving profile
    console.log('Profile saved:', this.profile);
    console.log('Settings saved:', this.settings);
  }
} 