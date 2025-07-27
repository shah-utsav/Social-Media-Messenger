import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <div class="logo">
            <div class="logo-icon">💬</div>
            <h1>Social Messenger</h1>
          </div>
          <p class="auth-subtitle">Create your account to get started</p>
        </div>
        
        <form class="auth-form" (ngSubmit)="onRegister()">
          <div class="form-group">
            <label for="name">Full Name</label>
            <input 
              type="text" 
              id="name"
              [(ngModel)]="name"
              name="name"
              placeholder="Enter your full name"
              required
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label for="email">Email</label>
            <input 
              type="email" 
              id="email"
              [(ngModel)]="email"
              name="email"
              placeholder="Enter your email"
              required
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label for="password">Password</label>
            <input 
              type="password" 
              id="password"
              [(ngModel)]="password"
              name="password"
              placeholder="Create a password"
              required
              class="form-input"
            >
          </div>
          
          <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input 
              type="password" 
              id="confirmPassword"
              [(ngModel)]="confirmPassword"
              name="confirmPassword"
              placeholder="Confirm your password"
              required
              class="form-input"
            >
          </div>
          
          <div class="form-options">
            <label class="checkbox-label">
              <input type="checkbox" [(ngModel)]="agreeToTerms" name="agreeToTerms">
              <span class="checkmark"></span>
              I agree to the Terms of Service
            </label>
          </div>
          
          <button type="submit" class="auth-btn" [disabled]="isLoading || !agreeToTerms">
            <span *ngIf="!isLoading">Create Account</span>
            <span *ngIf="isLoading">Creating account...</span>
          </button>
        </form>
        
        <div class="auth-footer">
          <p>Already have an account? <a routerLink="/login" class="link">Sign in</a></p>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  name: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  agreeToTerms: boolean = false;
  isLoading: boolean = false;

  constructor(private router: Router) {}

  onRegister() {
    if (!this.name || !this.email || !this.password || !this.agreeToTerms) return;
    if (this.password !== this.confirmPassword) return;
    
    this.isLoading = true;
    
    // Simulate API call
    setTimeout(() => {
      this.isLoading = false;
      this.router.navigate(['/chat']);
    }, 1500);
  }
} 