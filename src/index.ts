import { registerPlugin } from '@capacitor/core';

import type { ThinkerNlbSoftPosPlugin } from './definitions';

const ThinkerNlbSoftPos = registerPlugin<ThinkerNlbSoftPosPlugin>('ThinkerNlbSoftPos', {
  web: () => import('./web').then((m) => new m.ThinkerNlbSoftPosWeb()),
});

export * from './definitions';
export { ThinkerNlbSoftPos };
