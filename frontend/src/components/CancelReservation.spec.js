import { shallowMount } from '@vue/test-utils';
import CancelReservation from '@/components/CancelReservation.vue';

describe('CancelReservation.vue', () => {
  it('renders a button to cancel reservation', () => {
    const wrapper = shallowMount(CancelReservation);
    const cancelButton = wrapper.find('button.cancel-button');
    expect(cancelButton.exists()).toBe(true);
  });

  let wrapper;

  beforeEach(() => {
    wrapper = shallowMount(CancelReservation);
    jest.clearAllMocks(); // Clears any previous calls to the mocks
  });

  it('renders the cancellation form', () => {
    expect(wrapper.find('.cancel-form').exists()).toBe(true);
  });

  it('calls cancelReservation method when the form is submitted', async () => {
    const cancelReservationSpy = jest.spyOn(wrapper.vm, 'cancelReservation');
    await wrapper.find('.cancel-form').trigger('submit.prevent');
    expect(cancelReservationSpy).toHaveBeenCalled();
  });
  // Weitere Tests hier hinzufügen...
});
