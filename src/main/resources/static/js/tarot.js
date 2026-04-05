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

// ★ 스프레드별 힌트 — 예시는 여기에만 추가하면 됩니다
const spreadHints = {
    ONE: {
        fixed: '카드 단 한장으로 하나의 답을 보여줍니다.',
        examples: [
            '데일리 카드 뽑아줘.',
            '나 지금 썸남에게 고백할까?',
            '이거 지금 할까?'
        ]
    },
    THREE: {
        fixed: '3장의 카드로 과거 · 현재 · 미래를 읽어드립니다.',
        examples: [
            '이 사람과의 관계는 어떻게 흘러갈까?',
            '이 일을 계속 해야 할까?',
            '지금 내 상황이 어디서 왔고 어디로 가는지 알고 싶어.'
        ]
    },
    FIVE: {
        fixed: '5장의 카드로 상황을 심층 분석합니다.',
        examples: [
            '이 프로젝트 성공할 수 있을까?',
            '내 커리어 방향이 맞는 걸까?',
            '이 선택이 옳은 건지 알고 싶어.'
        ]
    },
    SEVEN: {
        fixed: '7장의 카드로 상대방의 속마음을 읽어드립니다.',
        examples: [
            '그 사람은 나를 어떻게 생각할까?',
            '썸남/썸녀가 나에게 호감이 있을까?',
            '그 사람이 나를 아직 생각할까?'
        ]
    },
    TEN: {
        fixed: '10장의 카드로 가장 심층적인 분석을 해드립니다.',
        examples: [
            '지금 내 인생에서 가장 중요한 것은?',
            '내가 가야 할 진짜 방향은 무엇인가?',
            '이 관계의 본질과 결말은 무엇인가?'
        ]
    }
};

function buildHint(spreadValue) {
    const hint = spreadHints[spreadValue];
    const hintFixed = document.getElementById('hintFixed');
    const hintScroll = document.getElementById('hintScroll');
    if (!hint || !hintFixed || !hintScroll) return;

    hintFixed.textContent = hint.fixed;
    const doubled = [...hint.examples, ...hint.examples];
    hintScroll.innerHTML = doubled.map(q => `<span>${q}</span>`).join('');
    hintScroll.style.animationDuration = (hint.examples.length * 2.5) + 's';
}

document.addEventListener('DOMContentLoaded', function () {
    const radios = document.querySelectorAll('input[name="spread"]');
    const textarea = document.querySelector('textarea[name="question"]');
    if (!radios.length || !textarea) return;

    function updateUI() {
        const spreadHint = document.getElementById('spreadHint');
        const selected = document.querySelector('input[name="spread"]:checked');
        const isEmpty = textarea.value.trim() === '';
        const isFocused = document.activeElement === textarea;

        if (selected) buildHint(selected.value);

        if (spreadHint) {
            spreadHint.classList.toggle('visible', isEmpty && !isFocused);
        }

        textarea.placeholder = '';
    }

    radios.forEach(r => r.addEventListener('change', updateUI));
    textarea.addEventListener('focus', updateUI);
    textarea.addEventListener('blur', updateUI);
    textarea.addEventListener('input', updateUI);
    updateUI();
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
