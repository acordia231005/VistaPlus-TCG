# VistaPlus Design System

## Concept
A premium, cinematic media platform with a "Glassmorphism" aesthetic. The interface should feel like a high-end streaming service (e.g., Netflix, Apple TV+) combined with modern community features.

## Color Palette
- **Background**: Deep Space (`#0b1120`)
- **Primary**: Electric Blue (`#3b82f6`) to Deep Indigo (`#1d4ed8`) gradient.
- **Surface**: Translucent Slate (`rgba(30, 41, 59, 0.4)`) with high-blur backdrop filter.
- **Accent**: Crimson Red (`#ef4444`) for high-priority actions/badges.
- **Text**: 
  - Primary: Off-White (`#f1f5f9`)
  - Secondary: Muted Slate (`#94a3b8`)

## Typography
- **Font Family**: `Outfit`, sans-serif (Geometric and modern).
- **Headings**: Semi-Bold to Bold, high-contrast.

## Shape & Effects
- **Corners**: Large radius (`16px` for cards, `12px` for buttons).
- **Borders**: Subtle, translucent white (`rgba(255, 255, 255, 0.08)`).
- **Shadows**: Deep, layered shadows for depth (`box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5)`).
- **Glassmorphism**: `backdrop-filter: blur(16px)` on all floating/surface elements.

## Animations
- **Transitions**: Smooth `0.4s cubic-bezier(0.25, 0.8, 0.25, 1)`.
- **Hover**: Lift effect (translateY), subtle scale up, and glow.
- **Loading**: Pulse and shimmer effects (skeleton screens).

## Components
- **Header**: Sticky, glass background, minimalist navigation.
- **Cards**: Aspect ratio 2:3 for posters, 16:9 for banners. Content revealed on hover.
- **Badges**: Pill-shaped, translucent backgrounds.
- **Buttons**: Gradient backgrounds with subtle hover glow.
