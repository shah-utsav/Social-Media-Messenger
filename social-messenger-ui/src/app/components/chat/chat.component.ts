import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface User {
  id: number;
  name: string;
  status: 'online' | 'offline' | 'away';
  avatar: string;
}

interface Message {
  id: number;
  sender: User;
  content: string;
  timestamp: Date;
  type: 'text' | 'image' | 'file';
  reactions: { emoji: string; count: number }[];
  isReply?: boolean;
  repliedTo?: Message;
}

interface Chat {
  id: number;
  name: string;
  type: 'direct' | 'group';
  participants: User[];
  lastMessage?: Message;
  unreadCount: number;
  isActive: boolean;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, AfterViewChecked {
  @ViewChild('messageContainer') private messageContainer!: ElementRef;
  @ViewChild('messageInput') private messageInput!: ElementRef;

  currentUser: User = {
    id: 1,
    name: 'John Doe',
    status: 'online',
    avatar: '👤'
  };

  chats: Chat[] = [
    {
      id: 1,
      name: 'Alice Johnson',
      type: 'direct',
      participants: [
        this.currentUser,
        { id: 2, name: 'Alice Johnson', status: 'online', avatar: '👩' }
      ],
      unreadCount: 2,
      isActive: true
    },
    {
      id: 2,
      name: 'Bob Smith',
      type: 'direct',
      participants: [
        this.currentUser,
        { id: 3, name: 'Bob Smith', status: 'away', avatar: '👨' }
      ],
      unreadCount: 0,
      isActive: false
    },
    {
      id: 3,
      name: 'Project Team',
      type: 'group',
      participants: [
        this.currentUser,
        { id: 2, name: 'Alice Johnson', status: 'online', avatar: '👩' },
        { id: 3, name: 'Bob Smith', status: 'away', avatar: '👨' },
        { id: 4, name: 'Carol Davis', status: 'offline', avatar: '👩‍💼' }
      ],
      unreadCount: 5,
      isActive: false
    }
  ];

  selectedChat: Chat | null = null;
  messages: Message[] = [];
  newMessage: string = '';
  isTyping: boolean = false;
  searchQuery: string = '';

  // Mock messages for demonstration
  private mockMessages: { [chatId: number]: Message[] } = {
    1: [
      {
        id: 1,
        sender: { id: 2, name: 'Alice Johnson', status: 'online', avatar: '👩' },
        content: 'Hey! How are you doing?',
        timestamp: new Date(Date.now() - 300000),
        type: 'text',
        reactions: []
      },
      {
        id: 2,
        sender: this.currentUser,
        content: 'I\'m doing great! Just working on some exciting projects. How about you?',
        timestamp: new Date(Date.now() - 240000),
        type: 'text',
        reactions: [{ emoji: '👍', count: 1 }]
      },
      {
        id: 3,
        sender: { id: 2, name: 'Alice Johnson', status: 'online', avatar: '👩' },
        content: 'That sounds amazing! I\'d love to hear more about it.',
        timestamp: new Date(Date.now() - 180000),
        type: 'text',
        reactions: []
      }
    ],
    2: [
      {
        id: 4,
        sender: { id: 3, name: 'Bob Smith', status: 'away', avatar: '👨' },
        content: 'Hi there! Are you free for a quick call?',
        timestamp: new Date(Date.now() - 600000),
        type: 'text',
        reactions: []
      }
    ],
    3: [
      {
        id: 5,
        sender: { id: 2, name: 'Alice Johnson', status: 'online', avatar: '👩' },
        content: 'Team meeting tomorrow at 10 AM!',
        timestamp: new Date(Date.now() - 900000),
        type: 'text',
        reactions: [{ emoji: '✅', count: 2 }, { emoji: '👍', count: 1 }]
      },
      {
        id: 6,
        sender: { id: 4, name: 'Carol Davis', status: 'offline', avatar: '👩‍💼' },
        content: 'I\'ll prepare the presentation slides.',
        timestamp: new Date(Date.now() - 800000),
        type: 'text',
        reactions: []
      }
    ]
  };

  ngOnInit() {
    console.log('Chat component initialized');
    // Select the first chat by default
    if (this.chats.length > 0) {
      this.selectChat(this.chats[0]);
    }
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  selectChat(chat: Chat) {
    this.selectedChat = chat;
    this.messages = this.mockMessages[chat.id] || [];
    
    // Mark chat as active and clear unread count
    this.chats.forEach(c => c.isActive = false);
    chat.isActive = true;
    chat.unreadCount = 0;
    
    // Focus on message input
    setTimeout(() => {
      this.messageInput?.nativeElement?.focus();
    }, 100);
  }

  sendMessage() {
    if (!this.newMessage.trim() || !this.selectedChat) return;

    const message: Message = {
      id: Date.now(),
      sender: this.currentUser,
      content: this.newMessage.trim(),
      timestamp: new Date(),
      type: 'text',
      reactions: []
    };

    this.messages.push(message);
    
    // Add to mock messages
    if (!this.mockMessages[this.selectedChat.id]) {
      this.mockMessages[this.selectedChat.id] = [];
    }
    this.mockMessages[this.selectedChat.id].push(message);

    this.newMessage = '';
    this.scrollToBottom();
  }

  addReaction(message: Message, emoji: string) {
    const existingReaction = message.reactions.find(r => r.emoji === emoji);
    if (existingReaction) {
      existingReaction.count++;
    } else {
      message.reactions.push({ emoji, count: 1 });
    }
  }

  get filteredChats(): Chat[] {
    if (!this.searchQuery.trim()) return this.chats;
    
    return this.chats.filter(chat => 
      chat.name.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  private scrollToBottom(): void {
    try {
      this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
    } catch (err) {}
  }

  formatTime(date: Date): string {
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  }

  formatDate(date: Date): string {
    const today = new Date();
    const yesterday = new Date(today);
    yesterday.setDate(yesterday.getDate() - 1);

    if (date.toDateString() === today.toDateString()) {
      return 'Today';
    } else if (date.toDateString() === yesterday.toDateString()) {
      return 'Yesterday';
    } else {
      return date.toLocaleDateString();
    }
  }

  trackByMessageId(index: number, message: Message): number {
    return message.id;
  }
} 