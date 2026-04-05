// 달 & 별 배경 생성
document.addEventListener('DOMContentLoaded', function () {
    const starsEl = document.getElementById('stars');
    if (!starsEl) return;

    // 별 생성
    for (let i = 0; i < 70; i++) {
        const star = document.createElement('div');
        const size = Math.random() < 0.15 ? 2 : 1;
        star.style.cssText = `
            position: absolute;
            width: ${size}px;
            height: ${size}px;
            background: rgba(255,255,255,${0.4 + Math.random() * 0.6});
            border-radius: 50%;
            top: ${Math.random() * 100}%;
            left: ${Math.random() * 100}%;
            animation: twinkle ${2 + Math.random() * 4}s ease-in-out infinite;
            animation-delay: ${Math.random() * 4}s;
        `;
        starsEl.appendChild(star);
    }

    // 달빛 조각 생성 (초승달 모양 글자)
    const moonSymbols = ['☽', '☾', '◐', '◑'];
    for (let i = 0; i < 6; i++) {
        const moon = document.createElement('div');
        moon.textContent = moonSymbols[Math.floor(Math.random() * moonSymbols.length)];
        moon.style.cssText = `
            position: absolute;
            font-size: ${10 + Math.random() * 14}px;
            color: rgba(201,168,76,${0.08 + Math.random() * 0.12});
            top: ${Math.random() * 100}%;
            left: ${Math.random() * 100}%;
            animation: floatMoon ${6 + Math.random() * 6}s ease-in-out infinite;
            animation-delay: ${Math.random() * 6}s;
            pointer-events: none;
            user-select: none;
        `;
        starsEl.appendChild(moon);
    }
});

const style = document.createElement('style');
style.textContent = `
    @keyframes twinkle {
        0%, 100% { opacity: 0.3; }
        50% { opacity: 1; }
    }
    @keyframes floatMoon {
        0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.5; }
        50% { transform: translateY(-12px) rotate(10deg); opacity: 1; }
    }
`;
document.head.appendChild(style);
